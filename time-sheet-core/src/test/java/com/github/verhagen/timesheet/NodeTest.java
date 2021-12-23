package com.github.verhagen.timesheet;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NodeTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @Test
    void createDepthOne() {
        Node<String, LocalDate, Activity> root = new Node<>("__root__");
        Activity activity = createActivity("sample", "2020.12.20", 30);
        root.add(activity.getNamePath(), activity.getDate(), activity);

        assertEquals("__root__", root.getPath());
        assertEquals("sample", root.getNode("sample".split("\\.")).getPath());
    }


    @Test
    void createDepthTwo() {
        Node<String, LocalDate, Activity> root = new Node<>("__root__");
        Activity activity = createActivity("sample.project","2020.12.10", 10);
        root.add(activity.getNamePath(), activity.getDate(), activity);

        assertEquals("__root__", root.getPath());
        assertEquals("sample", root.getNode("sample".split("\\.")).getPath());
        Node<String, LocalDate, Activity> node = root.getNode("sample.project".split("\\."));
        assertEquals("project", node.getPath());
        Set entryDates = node.getEntryKeys();
        assertTrue(entryDates.contains(LocalDate.parse("2020.12.10", formatter)));
    }

    private Activity createActivity(String name, String date, int minutes) {
        return new Activity.Builder()
                .add(name)
                .add(LocalDate.parse(date, formatter))
                .add(minutes)
                .create();
    }

}
