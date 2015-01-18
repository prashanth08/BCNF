import java.util.*;
import junit.framework.TestCase;

public class Test extends TestCase {

	/**
	 * Performs a basic test on a simple table.
	 * gives input (a,b,c), a->c
	 * and expects output (a,c),(a,c) or any reordering
	 **/
	public void testSimpleBCNF() {
		//construct table
		
		AttributeSet attrs = new AttributeSet();
		attrs.addAttribute(new Attribute("a"));
		attrs.addAttribute(new Attribute("b"));
		attrs.addAttribute(new Attribute("c"));

		//create functional dependencies
		Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
		AttributeSet ind = new AttributeSet();
		AttributeSet dep = new AttributeSet();
		ind.addAttribute(new Attribute("a"));
		dep.addAttribute(new Attribute("c"));
		FunctionalDependency fd = new FunctionalDependency(ind, dep);
		fds.add(fd);

		//run client code
		Set<AttributeSet> bcnf = BCNF.BCNF(attrs, fds);
		
		/*
		AttributeSet attrs = new AttributeSet();
        attrs.addAttribute(new Attribute("a"));
        attrs.addAttribute(new Attribute("b"));
        attrs.addAttribute(new Attribute("c"));
        attrs.addAttribute(new Attribute("d"));
        attrs.addAttribute(new Attribute("e"));
        //attrs.addAttribute(new Attribute("p"));
 
        //create functional dependencies
        Set<FunctionalDependency> fds = new HashSet<FunctionalDependency>();
        AttributeSet ind = new AttributeSet();
        AttributeSet dep = new AttributeSet();
        ind.addAttribute(new Attribute("b"));
        ind.addAttribute(new Attribute("c"));
        dep.addAttribute(new Attribute("a"));
        //dep.addAttribute(new Attribute("v"));
        FunctionalDependency fd = new FunctionalDependency(ind, dep);
        fds.add(fd);
 
 
        AttributeSet ind2 = new AttributeSet();
        AttributeSet dep2 = new AttributeSet();
        ind2.addAttribute(new Attribute("a"));
        dep2.addAttribute(new Attribute("e"));
        FunctionalDependency fd2 = new FunctionalDependency(ind2, dep2);
        fds.add(fd2);
 
        
        AttributeSet ind3 = new AttributeSet();
        AttributeSet dep3 = new AttributeSet();
        ind3.addAttribute(new Attribute("d"));
        ind3.addAttribute(new Attribute("e"));
        dep3.addAttribute(new Attribute("a"));
        FunctionalDependency fd3 = new FunctionalDependency(ind3, dep3);
        fds.add(fd3);
 
 
        /*
        AttributeSet ind4 = new AttributeSet();
        AttributeSet dep4 = new AttributeSet();
        ind4.addAttribute(new Attribute("c"));
        ind4.addAttribute(new Attribute("d"));
        dep4.addAttribute(new Attribute("i"));
        FunctionalDependency fd4 = new FunctionalDependency(ind4, dep4);
        fds.add(fd4);
 
 
        AttributeSet ind5 = new AttributeSet();
        AttributeSet dep5 = new AttributeSet();
        ind5.addAttribute(new Attribute("e"));
        dep5.addAttribute(new Attribute("c"));
        FunctionalDependency fd5 = new FunctionalDependency(ind5, dep5);
        fds.add(fd5);
 		
 
        Set<AttributeSet> bcnf = BCNF.BCNF(attrs,fds);
        */
		System.out.println(bcnf);
		//verify output
		assertEquals("Incorrect number of tables", 2, bcnf.size());

		for(AttributeSet as : bcnf) {
			assertEquals("Incorrect number of attributes", 2, as.size());
			assertTrue("Incorrect table", as.contains(new Attribute("a")));
		}
	
	}

}
