package DesigniteTests.utils.models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Designite.utils.models.Edge;
import Designite.utils.models.Graph;
import Designite.utils.models.Vertex;

public class GraphTest {
	
	private Graph graph;
	
	@Before
	public void setup() {
		Vertex vertex1 = new Vertex(){};
		Vertex vertex2 = new Vertex(){};
		Vertex vertex3 = new Vertex(){};
		Vertex vertex4 = new Vertex(){};
		Vertex vertex5 = new Vertex(){};
		Vertex vertex6 = new Vertex(){};
		Vertex vertex7 = new Vertex(){};
		Vertex vertex8 = new Vertex(){};
		Vertex vertex9 = new Vertex(){};
		Vertex vertex10 = new Vertex(){};
		
		Edge edge1 = new Edge(vertex1, vertex2);
		Edge edge2 = new Edge(vertex1, vertex4);
		Edge edge3 = new Edge(vertex2, vertex3);
		Edge edge4 = new Edge(vertex2, vertex4);
		Edge edge5 = new Edge(vertex3, vertex4);
		Edge edge6 = new Edge(vertex5, vertex6);
		Edge edge7 = new Edge(vertex6, vertex9);
		Edge edge8 = new Edge(vertex7, vertex8);
		Edge edge9 = new Edge(vertex7, vertex9);
		Edge edge10 = new Edge(vertex8, vertex9);
		
		List<Vertex> vertices = new ArrayList<>(Arrays.asList(
				vertex1,
				vertex2,
				vertex3,
				vertex4,
				vertex5,
				vertex6,
				vertex7,
				vertex8,
				vertex9,
				vertex10
				));
		
		List<Edge> edges = new ArrayList<>(Arrays.asList(
				edge1,
				edge2,
				edge3,
				edge4,
				edge5,
				edge6,
				edge7,
				edge8,
				edge9,
				edge10
				));
		
		graph = new Graph(vertices, edges);
	}
	
	@Test
	public void testNumberOfComponents() {
		int expected = 3;
		int actual = graph.getConnectedComponnents().size();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDegreeOfFirstComponent() {
		int expected = 4;
		int actual = graph.getConnectedComponnents().get(0).size();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDegreeOfSecondComponent() {
		int expected = 5;
		int actual = graph.getConnectedComponnents().get(1).size();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testDegreeOfThirdComponent() {
		int expected = 1;
		int actual = graph.getConnectedComponnents().get(2).size();
		
		assertEquals(expected, actual);
	}
}