package com.github.verhagen.timesheet;

import java.util.*;

public class Node<I1, I2, E> {
	private I1 pathName;
	private Map<I1, Node<I1, I2, E>> childNodes = new LinkedHashMap<>();
	private Map<I2, List<E>> entries = new LinkedHashMap<>();

	public Node(I1 pathName) {
		this.pathName = pathName;
	}

	public void addNode(I1 index1, Node<I1, I2, E> node) {
		childNodes.put(index1, node);
	}

	public void add(I1[] index1path, I2 index2, E entry) {
		Iterator<I1> iter = Arrays.asList(index1path).iterator();
		addChildNode(iter, index2, entry);
	}

	private void addChildNode(Iterator<I1> iter, I2 index2, E entry) {
		I1 key1 = iter.next();
		if (! childNodes.containsKey(key1)) {
			childNodes.put(key1, new Node<>(key1));
		}
		if (iter.hasNext()) {
			childNodes.get(key1).addChildNode(iter, index2, entry);
		}
		else {
			if (! childNodes.get(key1).entries.containsKey(index2)) {
				childNodes.get(key1).entries.put(index2, new ArrayList<>());
			}
			childNodes.get(key1).entries.get(index2).add(entry);
		}
	}

	public void accept(Visitor<Node<I1, I2, E>> visitor) {
		visitor.visit(this);
	}

	public I1 getPath() {
		return pathName;
	}

	public Node<I1, I2, E> getNode(I1[] index1) {
		List<I1> pathNames = (List<I1>) Arrays.asList(index1);
		return getChildNode(pathNames.iterator());
	}

	public Map<I2, List<E>> getEntries(I1[] pathNamesArr) {
		List<I1> pathNames = (List<I1>) Arrays.asList(pathNamesArr);
		return getEntries(pathNames);
	}
	public Map<I2, List<E>> getEntries(List<I1> pathNames) {
		return getChildNode(pathNames.iterator()).entries;
	}

	private Node<I1, I2, E> getChildNode(Iterator<I1> iter) {
		I1 key1 = iter.next();
		if (iter.hasNext()) {
			return childNodes.get(key1).getChildNode(iter);
		}
		return childNodes.get(key1);
	}

	public Set<I2> getEntryKeys() {
		return entries.keySet();
	}
}
