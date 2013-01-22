package tests;

import junit.framework.Assert;
import graph.*;

import static org.junit.Assert.*;
import graph.Graph;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import district.DistrictPlanner;
import district.Groundplan;

public class GraphCloneTest {
	DistrictPlanner planner;
	Graph testgraph;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		planner = new DistrictPlanner();
		testgraph = new Graph(planner.planWijk());
		testgraph.setNearestNeighbours();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGraphClone() {
		Graph clone =testgraph.clone();
		Assert.assertEquals(testgraph.getVertices(), clone.getVertices());
	}

}
