package util;

import java.awt.*;
import java.util.Objects;

public class PathNode {
    private static PathNode reverseHead;
    public PathNode next;
    public int size;
    public Point p;

    public PathNode(Point p) {
        this.p = p;
    }

    public PathNode(Point p, PathNode node) {
        next = node;
        size = node.size + 1;
        this.p = p;
    }

    public static PathNode reverse(PathNode node) {
        reverseRecursion(node).next = null;
        return reverseHead;
    }

    private static PathNode reverseRecursion(PathNode node) {
        if (node.next == null) {
            reverseHead = node;
            return node;
        } else {
            PathNode out = reverseRecursion(node.next);
            out.next = node;
            return node;
        }
    }

    public boolean equals(Object o) {
        if (o instanceof PathNode) {
            return this.p.equals(((PathNode) o).p);
        }
        return false;
    }

    public String toString() {
        if (next == null) {
            return p.toString();
        }
        return p.toString() + '\n' + next.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(p);
    }
}
