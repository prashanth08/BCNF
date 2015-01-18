/*
 * referred the following for power set implementation
 * http://stackoverflow.com/questions/1670862/obtaining-a-powerset-of-a-set-in-java
 */
import java.util.*;

public class BCNF {

	/**
	 * Implement your algorithm here
	 **/
	

	public static Set<AttributeSet> BCNF(AttributeSet attrs, Set<FunctionalDependency> fds) {
		 Set<AttributeSet> returnAttributeSet = new HashSet<AttributeSet>();

			 Set<AttributeSet> powerSet = powerSet(attrs);
			 
			 for (AttributeSet x :powerSet) {
			     //System.out.println(x);
			     
			     AttributeSet closureX = closure(x,fds);
			     
			     AttributeSet tempClosureX = new AttributeSet();
			     //Remove any attributes that are there in fd but not in attr
			     for(Attribute a : closureX.getAttributeList()){
			    	 if(attrs.contains(a)){
			    		 tempClosureX.addAttribute(a);
			    	 }
			     }
			     
			     closureX = tempClosureX;
			     
			     if(closureX.equals(x) || closureX.equals(attrs)){
			    	 continue;
			     }

			     ArrayList<Attribute> xCompList = new ArrayList<Attribute>(x.getAttributeList());
			     
			     for(Attribute a : attrs.getAttributeList()){
			    	 if(!closureX.contains(a)){
			    		 xCompList.add(a);
			    	 }
			     }

			     AttributeSet closureXComp = new AttributeSet(xCompList);

			     returnAttributeSet.addAll(BCNF(closureX,fds));
			     returnAttributeSet.addAll(BCNF(closureXComp,fds));
			     returnAttributeSet = cleanUp(returnAttributeSet);
			     return returnAttributeSet;
			     
			 }
			 
		 returnAttributeSet.add(attrs);
		 returnAttributeSet = cleanUp(returnAttributeSet);
		 return returnAttributeSet;
	}

	private static Set<AttributeSet> cleanUp(Set<AttributeSet> returnAttributeSet) {
		
		Set<AttributeSet> cleanedSet = new HashSet<AttributeSet>();
		for(AttributeSet attrSet : returnAttributeSet){
			AttributeSet cleanedAttSet = new AttributeSet();
			for(Attribute a: attrSet.getAttributeList()){
				if(!a.toString().isEmpty()){
					cleanedAttSet.addAttribute(a);
				}
			}
			cleanedSet.add(cleanedAttSet);
		}
		return cleanedSet;
	}

	/**
	 * Recommended helper method
	 **/
	public static AttributeSet closure(AttributeSet attrs, Set<FunctionalDependency> fds) {
		
		//List['A'] = { w -> z } id A in w
		HashMap<String,Set<FunctionalDependency>> list = new HashMap<String,Set<FunctionalDependency>>();
		
		//Stores count[w->z] = |w|
		HashMap<String,Integer> count = new HashMap<String,Integer>();
		
		//Initial computation to compute List and Count
		for(FunctionalDependency fd : fds){
			count.put(fd.toString(), fd.getIndependent().size());
			
			Iterator<Attribute> indIter = fd.getIndependent().iterator();

			while(indIter.hasNext()){
				
				Attribute ind = indIter.next();
				
				if(!list.containsKey(ind.toString())){
					//no key yet
					
					Set<FunctionalDependency> fundeps = new HashSet<FunctionalDependency>();
					fundeps.add(fd);
					
					list.put(ind.toString(), fundeps );
				}
				else
				{
					list.get(ind.toString()).add(fd);
				}
			}
				
		}
		
		AttributeSet update = new AttributeSet(attrs);
		AttributeSet newDep = new AttributeSet(attrs);
		
		while(update.size() != 0){
			Attribute A = update.getAttributeList().get(0);
			update.getAttributeList().remove(0);
			
			if(list.containsKey(A.toString())){
				for(FunctionalDependency fd : list.get(A.toString())){
					
					int updatedCount = count.get(fd.toString()) - 1;
					count.put(fd.toString(), updatedCount);
					
					if ( updatedCount == 0){
						AttributeSet Z = fd.dependent();
						AttributeSet add = new AttributeSet();
						
						for(Attribute a : Z.getAttributeList()){
							if(!newDep.contains(a)){
								add.addAttribute(a);
							}
						}
						
						for(Attribute a : add.getAttributeList()){
							newDep.addAttribute(a);
							update.addAttribute(a);
						}
					}
					
				}
			}
			
		}

		return newDep;
	}
	
	
	public static Set<AttributeSet> powerSet(AttributeSet attrs) {
		//Set<AttributeSet> sets = new HashSet<AttributeSet>();
        Set<AttributeSet> sets = new TreeSet<AttributeSet>(new Comparator<AttributeSet>() {
            @Override
            public int compare(AttributeSet o1, AttributeSet o2) {
                if(o1.size() > o2.size()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
	    if (attrs.size() == 0) {
	    	sets.add(new AttributeSet(attrs));
	    	return sets;
	    }
	    
	    List<Attribute> list = new ArrayList<Attribute>(attrs.getAttributeList());
	    
	    Attribute head = list.get(0);
	    AttributeSet rest = new AttributeSet(list.subList(1, list.size()));
	    for (AttributeSet set : powerSet(rest)) {
	    	AttributeSet newSet = new AttributeSet();
	    	newSet.addAttribute(head);
	    	newSet.appendAttributeSet(set);
	    	sets.add(newSet);
	    	sets.add(set);
	    }		
	    return sets;
	}
	
}
