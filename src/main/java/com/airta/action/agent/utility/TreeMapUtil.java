package com.airta.action.agent.utility;

import com.airta.action.agent.entity.DriverConfig;
import com.airta.action.agent.entity.html.Element;
import com.airta.action.agent.entity.html.ElementType;

import java.util.List;

public class TreeMapUtil {

    private static void info(String info) {
        System.out.println("## " + info);
    }

    public static Element addElement(Element parentElement, int depth, ElementType type, String value, int index) {

        Element childElement = new Element();
        parentElement.getChildren().add(childElement);
        parentElement.increaseChildrenCount();

        childElement.setDepth(depth);
        childElement.setParentId(parentElement.getElementId());
        childElement.setActionable(true);
        childElement.setType(type);
        switch (type) {
            case link:
            case image:
            case form:
                childElement.setUrl(value);
                break;
            default:
                break;
        }

        childElement.setElementId(childElement.getType() + "." + depth + "_" + index);
        return childElement;
    }

    public static void addElement(Element parentElement, List<String> elementsList, int depth, ElementType type) {

        if (!elementsList.isEmpty()) {
            for (int index = 0; index < elementsList.size(); index++) {
                if (depth > DriverConfig.CHILDPAGE_DEPTH) {
                    info("Out of Depth Range, jump out ..");
                    return;
                }
                if (elementsList.get(index).endsWith(".pdf") || elementsList.get(index).endsWith(".doc")) {
                    info("File type link, jump out ..");
                    return;
                }
                addElement(parentElement, depth, type, elementsList.get(index), index);
            }
        }
    }
}
