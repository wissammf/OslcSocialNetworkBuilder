package com.wissam.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.jena.ext.com.google.common.base.Strings;

import com.wissam.model.Constants;
import com.wissam.model.CoreResource;
import com.wissam.model.ElementWithResource;
import com.wissam.model.Filters;
import com.wissam.model.Relationship;
import com.wissam.model.RelationshipDestinationPair;
import com.wissam.model.RelationshipPath;
import com.wissam.model.changerequest.ChangeRequest;
import com.wissam.model.graph.Edge;
import com.wissam.model.graph.Graph;
import com.wissam.model.graph.Node;
import com.wissam.model.graph.gexf.Gexf;
import com.wissam.model.graph.gexf.GexfEdge;
import com.wissam.model.graph.gexf.GexfGraph;
import com.wissam.model.graph.gexf.GexfMeta;
import com.wissam.model.graph.gexf.GexfNode;
import com.wissam.model.testcase.TestCase;
import com.wissam.network.OslcDataFetcher;
import com.wissam.xmlutils.GexfSerializer;
import com.wissam.xmlutils.OslcDataSerializer;

public class MainController {
	private static final int MAX_PATH_LENGTH = 3;

	public Graph createSnFromTestCase(String username, String password, String url, Filters filters) {
		Graph graph = new Graph();

		RelationshipPath path = new RelationshipPath();

		HashSet<String> visitedResources = new HashSet<>();

		includeTestCase(graph, visitedResources, path, Relationship.Initial, username, password, url, filters);

		// Create gexf graph
		Gexf gexf = createGexfGraph(graph);
		GexfSerializer.writeGexfToFile(gexf, null);

		return graph;
	}

	public Graph createSnFromChangeRequest(String username, String password, String url, Filters filters) {
		Graph graph = new Graph();

		RelationshipPath path = new RelationshipPath();

		HashSet<String> visitedResources = new HashSet<>();

		includeChangeRequest(graph, visitedResources, path, Relationship.Initial, username, password, url, filters);

		// Create gexf graph
		Gexf gexf = createGexfGraph(graph);
		GexfSerializer.writeGexfToFile(gexf, null);

		return graph;
	}

	private void includeTestCase(Graph graph, HashSet<String> visitedResources, RelationshipPath path,
			Relationship relationship, String username, String password, String uri, Filters filters) {
		// Fetch the test case
		OslcDataFetcher.loadTestCase(username, password, uri);
		// Deserialize the test case
		TestCase testCase = OslcDataSerializer.deserializeTestCase();

		if (!checkCommonFilters(testCase, filters))
			return;

		// Connect this test case to each resource in the path
		for (int i = 0; i < path.getSteps().size(); i++) {
			List<RelationshipDestinationPair> currentPath = new ArrayList<>(
					path.getSteps().subList(i, path.getSteps().size()));
			currentPath.add(new RelationshipDestinationPair(relationship, testCase));

			link2ElementsWithSocialAttributes(graph, path.getSteps().get(i).getDestination(), testCase, currentPath);
		}

		// If this node was visited previously, don't branch out of it again
		// Just connect to all the connections it has
		// Must connect all nodes on the path to all nodes that this node is
		// connected to, while not going over max path length
		if (visitedResources.contains(testCase.getUri())) {
			connectPathNodesToElementConnections(graph, path, testCase);

			return;
		}

		// Add to visited resources
		visitedResources.add(testCase.getUri());

		linkPeopleWithinResource(graph, testCase);

		// Add this test case to the new path
		RelationshipPath newPath = new RelationshipPath();
		newPath.getSteps().addAll(path.getSteps());
		newPath.getSteps().add(new RelationshipDestinationPair(relationship, testCase));

		// Stop if max length of connections reached, remove the first node and
		// continue
		if (newPath.getSteps().size() == MAX_PATH_LENGTH) {
			newPath.getSteps().remove(0);
		}

		// Continue into related change requests if they exist
		if (testCase.getRelatedChangeRequests() != null && testCase.getRelatedChangeRequests().size() > 0) {
			for (ElementWithResource relatedCr : testCase.getRelatedChangeRequests()) {
				if (isElementWithResourceValid(relatedCr))
					includeChangeRequest(graph, visitedResources, newPath, Relationship.TestCaseRelatedChangeRequest,
							username, password, relatedCr.getResourceUri(), filters);
			}
		}

		// Continue into tested change requests if they exist
		if (testCase.getTestsChangeRequest() != null && testCase.getTestsChangeRequest().size() > 0) {
			for (ElementWithResource testedCr : testCase.getTestsChangeRequest()) {
				if (isElementWithResourceValid(testedCr))
					includeChangeRequest(graph, visitedResources, newPath, Relationship.TestCaseTestsChangeRequest,
							username, password, testedCr.getResourceUri(), filters);
			}
		}

		// TODO: add tested requirements here
	}

	private void includeChangeRequest(Graph graph, HashSet<String> visitedResources, RelationshipPath path,
			Relationship relationship, String username, String password, String uri, Filters filters) {
		// Fetch the change request
		OslcDataFetcher.loadChangeRequest(username, password, uri);
		// Deserialize the change request
		ChangeRequest changeRequest = OslcDataSerializer.deserializeChangeRequest();

		if (!checkCommonFilters(changeRequest, filters))
			return;

		if (!checkChangeRequestFilters(changeRequest, filters))
			return;

		// Connect this change request to each resource in the path
		for (int i = 0; i < path.getSteps().size(); i++) {
			List<RelationshipDestinationPair> currentPath = new ArrayList<>(
					path.getSteps().subList(i, path.getSteps().size()));
			currentPath.add(new RelationshipDestinationPair(relationship, changeRequest));

			link2ElementsWithSocialAttributes(graph, path.getSteps().get(i).getDestination(), changeRequest,
					currentPath);
		}

		// If this node was visited previously, don't branch out of it again
		// Just connect to all the connections it has
		// Must connect all nodes on the path to all nodes that this node is
		// connected to, while not going over max path length
		if (visitedResources.contains(changeRequest.getUri())) {
			connectPathNodesToElementConnections(graph, path, changeRequest);

			return;
		}

		// Add to visited resources
		visitedResources.add(changeRequest.getUri());

		linkPeopleWithinResource(graph, changeRequest);

		// Add this change request to the new path
		RelationshipPath newPath = new RelationshipPath();
		newPath.getSteps().addAll(path.getSteps());
		newPath.getSteps().add(new RelationshipDestinationPair(relationship, changeRequest));

		// Stop if max length of connections reached, remove the first node and
		// continue
		if (newPath.getSteps().size() == MAX_PATH_LENGTH) {
			newPath.getSteps().remove(0);
		}

		// Continue into related change requests if they exist
		if (changeRequest.getRelatedChangeRequests() != null && changeRequest.getRelatedChangeRequests().size() > 0) {
			for (ElementWithResource relatedCr : changeRequest.getRelatedChangeRequests()) {
				if (isElementWithResourceValid(relatedCr))
					includeChangeRequest(graph, visitedResources, newPath,
							Relationship.ChangeRequestRelatedChangeRequest, username, password,
							relatedCr.getResourceUri(), filters);
			}
		}

		// Continue into tested change requests if they exist
		if (changeRequest.getTestedByTestCase() != null && changeRequest.getTestedByTestCase().size() > 0) {
			for (ElementWithResource testerTc : changeRequest.getTestedByTestCase()) {
				if (isElementWithResourceValid(testerTc))
					includeTestCase(graph, visitedResources, newPath, Relationship.ChangeRequestTestedByTestCase,
							username, password, testerTc.getResourceUri(), filters);
			}
		}

		// TODO: add implemented requirements here
	}

	/**
	 * Connects all nodes on the path to all nodes that the passed resource node
	 * is connected to, while not going over max path length.
	 * 
	 * @param graph
	 *            holder of nodes and edges
	 * @param path
	 *            holds nodes to be connected
	 * @param resource
	 *            holds nodes to be connected to
	 */
	private void connectPathNodesToElementConnections(Graph graph, RelationshipPath path, CoreResource resource) {
		// Get paths for edges going out of the creator (works for the
		// contributor as well)
		List<Edge> creatorEdges = new ArrayList<>(
				graph.getNodes().get(resource.getCreator().getResourceUri()).getOutgoingEdges().values());
		List<List<RelationshipDestinationPair>> creatorPaths = new ArrayList<>();
		for (Edge edge : creatorEdges) {
			creatorPaths.addAll(edge.getPaths());
		}

		// Work with a temp path to use to connect all previous nodes in the
		// path
		RelationshipPath tempPath = new RelationshipPath();
		tempPath.getSteps().addAll(path.getSteps());

		int i = 0;
		while (tempPath.getSteps().size() > 0 && i + tempPath.getSteps().size() < MAX_PATH_LENGTH) {
			// Get this nodes' connections with length = i
			List<List<RelationshipDestinationPair>> tempConnections = new ArrayList<>();
			for (List<RelationshipDestinationPair> creatorPath : creatorPaths) {
				if (creatorPath.size() == i) {
					tempConnections.add(creatorPath);
				}
			}

			// Connect the first node in the temp path to the last node
			// in this node's connection
			for (List<RelationshipDestinationPair> tempConnection : tempConnections) {
				link2ElementsWithSocialAttributes(graph, tempConnection.get(tempConnection.size() - 1).getDestination(),
						tempPath.getSteps().get(0).getDestination(), tempPath.getSteps());
			}

			// Now that the first node in the temp path has been
			// connected, remove it
			tempPath.getSteps().remove(0);

			i++;
		}
	}

	private boolean checkCommonFilters(CoreResource resource, Filters filters) {
		// Check created date
		if (filters.getCreatedFrom() != null) {
			if (resource.getCreated() == null) {
				return false;
			}

			if (resource.getCreated().compareTo(filters.getCreatedFrom()) < 0)
				return false;
		}

		if (filters.getCreatedTo() != null) {
			if (resource.getCreated() == null) {
				return false;
			}

			if (resource.getCreated().compareTo(filters.getCreatedTo()) > 0)
				return false;
		}

		// Check modified date
		if (filters.getLastModifiedFrom() != null) {
			if (resource.getModified() == null)
				return false;

			if (resource.getModified().compareTo(filters.getLastModifiedFrom()) < 0)
				return false;
		}

		if (filters.getLastModifiedTo() != null) {
			if (resource.getModified() == null)
				return false;

			if (resource.getModified().compareTo(filters.getLastModifiedTo()) > 0)
				return false;
		}

		// Check keywords
		if (filters.getKeywords().size() > 0) {
			if (resource.getSubject() == null)
				return false;

			for (String keyword : filters.getKeywords()) {
				if (!resource.getSubject().contains(keyword))
					return false;
			}
		}

		// Everything went well
		return true;
	}

	private boolean checkChangeRequestFilters(ChangeRequest changeRequest, Filters filters) {
		// If multiple states are selected by the user, the change request must
		// be at least one of them

		// If one state is selected and present in the change request, return
		// true
		if (filters.isClosed() && changeRequest.isClosed()) {
			return true;
		}

		if (filters.isInprogress() && changeRequest.isInprogress()) {
			return true;
		}

		if (filters.isFixed() && changeRequest.isFixed()) {
			return true;
		}

		if (filters.isApproved() && changeRequest.isApproved()) {
			return true;
		}

		if (filters.isReviewed() && changeRequest.isReviewed()) {
			return true;
		}

		if (filters.isVerified() && changeRequest.isVerified()) {
			return true;
		}

		// If at least one filter is selected, then the change request doesn't
		// fulfill the required filters
		if (filters.isClosed() || filters.isInprogress() || filters.isFixed() || filters.isApproved()
				|| filters.isReviewed() || filters.isVerified())
			return false;

		return true;
	}

	/**
	 * Adds nodes in the passed graph for creators, contributors and modifiedBy
	 * of passed resource, and adds edges between them
	 * 
	 * @param graph
	 *            holder of nodes and edges
	 * @param res
	 *            resource with people
	 */
	private void linkPeopleWithinResource(Graph graph, CoreResource res) {
		// If creator isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(res.getCreator())) {
			graph.addNodeForUri(res.getCreator().getResourceUri());
		}

		// If contributor isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(res.getContributor())) {
			graph.addNodeForUri(res.getContributor().getResourceUri());
		}

		// If modifiedBy isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(res.getModifiedBy())) {
			graph.addNodeForUri(res.getModifiedBy().getResourceUri());
		}

		// Link creator and contributor
		if (isElementWithResourceValid(res.getCreator()) && isElementWithResourceValid(res.getContributor())) {
			graph.linkNodes(res.getCreator().getResourceUri(), res.getContributor().getResourceUri(),
					new ArrayList<>(Arrays.asList(new RelationshipDestinationPair(Relationship.Initial, res))));
			graph.linkNodes(res.getContributor().getResourceUri(), res.getCreator().getResourceUri(),
					new ArrayList<>(Arrays.asList(new RelationshipDestinationPair(Relationship.Initial, res))));
		}

		// Link creator and modifiedBy
		if (isElementWithResourceValid(res.getCreator()) && isElementWithResourceValid(res.getModifiedBy())) {
			graph.linkNodes(res.getCreator().getResourceUri(), res.getModifiedBy().getResourceUri(),
					new ArrayList<>(Arrays.asList(new RelationshipDestinationPair(Relationship.Initial, res))));
			graph.linkNodes(res.getModifiedBy().getResourceUri(), res.getCreator().getResourceUri(),
					new ArrayList<>(Arrays.asList(new RelationshipDestinationPair(Relationship.Initial, res))));
		}

		// Link contributor and modifiedBy
		if (isElementWithResourceValid(res.getContributor()) && isElementWithResourceValid(res.getModifiedBy())) {
			graph.linkNodes(res.getContributor().getResourceUri(), res.getModifiedBy().getResourceUri(),
					new ArrayList<>(Arrays.asList(new RelationshipDestinationPair(Relationship.Initial, res))));
			graph.linkNodes(res.getModifiedBy().getResourceUri(), res.getContributor().getResourceUri(),
					new ArrayList<>(Arrays.asList(new RelationshipDestinationPair(Relationship.Initial, res))));
		}
	}

	/**
	 * Adds nodes in the passed graph for creators, contributors and modifiedBy
	 * of passed elements, and adds edges between them
	 * 
	 * @param graph
	 *            holder of nodes and edges
	 * @param e1
	 *            first element
	 * @param e2
	 *            second element
	 */
	private void link2ElementsWithSocialAttributes(Graph graph, CoreResource e1, CoreResource e2,
			List<RelationshipDestinationPair> path) {
		// If e1 creator isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(e1.getCreator())) {
			graph.addNodeForUri(e1.getCreator().getResourceUri());
		}

		// If e1 contributor isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(e1.getContributor())) {
			graph.addNodeForUri(e1.getContributor().getResourceUri());
		}

		// If e1 modifiedBy isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(e1.getModifiedBy())) {
			graph.addNodeForUri(e1.getModifiedBy().getResourceUri());
		}

		// If e2 creator isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(e2.getCreator())) {
			graph.addNodeForUri(e2.getCreator().getResourceUri());
		}

		// If e2 contributor isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(e2.getContributor())) {
			graph.addNodeForUri(e2.getContributor().getResourceUri());
		}

		// If e2 modifiedBy isn't empty and doesn't have a node add node
		if (isElementWithResourceValid(e2.getModifiedBy())) {
			graph.addNodeForUri(e2.getModifiedBy().getResourceUri());
		}

		// Link e1 creator and e2 creator
		if (isElementWithResourceValid(e1.getCreator()) && isElementWithResourceValid(e2.getCreator())) {
			graph.linkNodes(e1.getCreator().getResourceUri(), e2.getCreator().getResourceUri(), path);
		}

		// Link e1 creator and e2 contributor
		if (isElementWithResourceValid(e1.getCreator()) && isElementWithResourceValid(e2.getContributor())) {
			graph.linkNodes(e1.getCreator().getResourceUri(), e2.getContributor().getResourceUri(), path);
		}

		// Link e1 creator and e2 modifiedBy
		if (isElementWithResourceValid(e1.getCreator()) && isElementWithResourceValid(e2.getModifiedBy())) {
			graph.linkNodes(e1.getCreator().getResourceUri(), e2.getModifiedBy().getResourceUri(), path);
		}

		// Link e1 contributor and e2 creator
		if (isElementWithResourceValid(e1.getContributor()) && isElementWithResourceValid(e2.getCreator())) {
			graph.linkNodes(e1.getContributor().getResourceUri(), e2.getCreator().getResourceUri(), path);
		}

		// Link e1 contributor and e2 contributor
		if (isElementWithResourceValid(e1.getContributor()) && isElementWithResourceValid(e2.getContributor())) {
			graph.linkNodes(e1.getContributor().getResourceUri(), e2.getContributor().getResourceUri(), path);
		}

		// Link e1 contributor and e2 modifiedBy
		if (isElementWithResourceValid(e1.getContributor()) && isElementWithResourceValid(e2.getModifiedBy())) {
			graph.linkNodes(e1.getContributor().getResourceUri(), e2.getModifiedBy().getResourceUri(), path);
		}

		// Link e1 modifiedBy and e2 creator
		if (isElementWithResourceValid(e1.getModifiedBy()) && isElementWithResourceValid(e2.getCreator())) {
			graph.linkNodes(e1.getModifiedBy().getResourceUri(), e2.getCreator().getResourceUri(), path);
		}

		// Link e1 modifiedBy and e2 contributor
		if (isElementWithResourceValid(e1.getModifiedBy()) && isElementWithResourceValid(e2.getContributor())) {
			graph.linkNodes(e1.getModifiedBy().getResourceUri(), e2.getContributor().getResourceUri(), path);
		}

		// Link e1 modifiedBy and e2 modifiedBy
		if (isElementWithResourceValid(e1.getModifiedBy()) && isElementWithResourceValid(e2.getModifiedBy())) {
			graph.linkNodes(e1.getModifiedBy().getResourceUri(), e2.getModifiedBy().getResourceUri(), path);
		}
	}

	/**
	 * Creates Gexf object corresponding to passed Graph object
	 * 
	 * @param graph
	 *            to be converted to Gexf object
	 * @return created Gexf object
	 */
	private Gexf createGexfGraph(Graph graph) {
		Gexf gexf = new Gexf();

		// Create meta object
		String lastModified = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		GexfMeta meta = new GexfMeta(lastModified, "Wissam", "Graph representing a social network based on OSLC data.");
		gexf.setMeta(meta);

		// Create graph object
		GexfGraph gexfGraph = new GexfGraph();
		ArrayList<GexfNode> nodes = new ArrayList<>();
		ArrayList<GexfEdge> edges = new ArrayList<>();

		for (Map.Entry<String, Node> nodeEntry : graph.getNodes().entrySet()) {
			Node node = nodeEntry.getValue();
			// Create gexf node
			String nodeId = node.getId();
			String nodeLabel = node.getUri().substring(node.getUri().lastIndexOf('/') + 1);
			GexfNode gexfNode = new GexfNode(nodeId, nodeLabel);

			// Loop through all outgoing edges from the node and create
			// corrisponding gexf edges
			for (Map.Entry<String, Edge> edgeEntry : node.getOutgoingEdges().entrySet()) {
				Edge edge = edgeEntry.getValue();
				// Create gexf edge
				String edgeId = edge.getId();
				String sourceNodeId = nodeId;
				String targetNodeId = edge.getDestination().getId();
				GexfEdge gexfEdge = new GexfEdge(edgeId, sourceNodeId, targetNodeId, edge.getWeight());

				edges.add(gexfEdge);
			}

			nodes.add(gexfNode);
		}

		gexfGraph.setNodes(nodes);
		gexfGraph.setEdges(edges);

		gexf.setGraph(gexfGraph);

		return gexf;
	}

	/**
	 * Checks whether resource of passed element is valid
	 * 
	 * @param e
	 *            passed element with resource
	 * @return result of the check
	 */
	private boolean isElementWithResourceValid(ElementWithResource e) {
		if (e != null && !Strings.isNullOrEmpty(e.getResourceUri())
				&& !e.getResourceUri().equals(Constants.UNASSIGNED)) {
			return true;
		}

		return false;
	}
}
