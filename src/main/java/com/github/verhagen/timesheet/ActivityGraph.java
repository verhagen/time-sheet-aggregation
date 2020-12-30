package com.github.verhagen.timesheet;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.util.mxCellRenderer;

public class ActivityGraph implements Visitor<Activity> {
	private static final ActivityNode NODE_ROOT = new ActivityNode("__root__");
	private static final ActivityNode NODE_NOTE = new ActivityNode("__note__");
	private Logger logger = LoggerFactory.getLogger(ActivityGraph.class);
	private DefaultDirectedGraph<ActivityNode, DefaultEdge> activityGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
//	private Map<String, Set<String>> activities = new HashMap<>();
	private static String word = "[a-zA-Z0-9-]+";
	private Pattern notePattern = Pattern.compile("^\\(.*\\)$");
	private Pattern activityPattern = Pattern.compile("^(" +  word + "(\\." + word +")*" + ")$");
//	private List<Pattern> patternsToIgnore = new ArrayList<>();


	public ActivityGraph() {
		activityGraph.addVertex(NODE_ROOT);
//		this(null);
	}
//
//	public ActivityExtractor(List<String> activitiesToIgnore) {
//		if (activitiesToIgnore == null) {
//			return;
//		}
//		for (String expression : activitiesToIgnore) {
//			expression = toRegularExpression(expression);
//			logger.info("Adding pattern to ignore: " + expression);
//			patternsToIgnore.add(Pattern.compile(expression));
//		}
//	}

	public static String toRegularExpression(String expression) {
		expression = expression.replace("*", word);
		expression = "^" + expression + "$";
		return expression;
	}


	@Override
	public void visit(Activity activity) {
		String activityNameCln = StringUtils.trimToNull(activity.getName());
		if (activityNameCln == null) {
			return;
		}
		Matcher matcher = notePattern.matcher(activityNameCln);
		if (matcher.matches()) {
			activityGraph.addEdge(NODE_ROOT, NODE_NOTE);
			logger.info("Found note '" + activityNameCln + "'");
			return;
		}
		matcher = activityPattern.matcher(activityNameCln);
		if (! matcher.matches()) {
			logger.warn("Found activity '" + activityNameCln + "' which does not follow the pattern '"
					+ activityPattern.pattern() + "'");
			return;
		}

		String[] names = activityNameCln.split("\\.");
//		logger.info("names: " + Arrays.asList(names));
		ActivityNode parentNode = NODE_ROOT;
		for (String name : names) {
			ActivityNode node = new ActivityNode(name);
			if (! activityGraph.containsVertex(node)) {
				activityGraph.addVertex(node);
			}
			activityGraph.addEdge(parentNode, node);
			parentNode = node;
		}
		parentNode.add(activity);
	}


	public void asPng(Path path) {
		File dataDir = path.getParent().toFile();
		File imgFile = path.toFile();
		if (! dataDir.exists()) {
			dataDir.mkdirs();
		}
		try {
			imgFile.createNewFile();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JGraphXAdapter<ActivityNode, DefaultEdge> graphAdapter = 
				new JGraphXAdapter<ActivityNode, DefaultEdge>(activityGraph);
	    mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
	    layout.execute(graphAdapter.getDefaultParent());
	    
	    BufferedImage image = 
	    		mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
	    try {
			ImageIO.write(image, "PNG", imgFile);
		}
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@Override
	public String toString() {
		DepthFirstIterator<ActivityNode, DefaultEdge> depthFirstIterator = new DepthFirstIterator<>(activityGraph);
		while (depthFirstIterator.hasNext()) {
			logger.info("toString()  " + depthFirstIterator.next());
			
		}
		return "todo";
	}

}
