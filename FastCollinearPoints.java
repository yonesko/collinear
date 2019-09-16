/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class FastCollinearPoints {
    private Node head, last;
    private int count;

    public FastCollinearPoints(Point[] points) {
        validate(points);
        Point[] aux = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            Arrays.sort(aux, point.slopeOrder());
            int l = -1, r = 0;
            for (int i = 0; i < aux.length; i++) {
                if (i + 1 < aux.length && point.slopeTo(aux[i]) == point.slopeTo(aux[i + 1])) {
                    if (l == -1) l = i;
                    r = i + 1;
                }
                else if (l != -1 && r - l + 1 >= 3) {
                    Point[] ls = new Point[r - l + 2];
                    for (int j = 0; j < ls.length - 1; j++) {
                        ls[j] = aux[l + j];
                    }
                    ls[ls.length - 1] = point;
                    Arrays.sort(ls);
                    addNode(new Node(new LineSegment(ls[0], ls[ls.length - 1])));
                    l = -1;
                }
            }
        }
    }

    private void validate(Point[] points) {
        if (points == null) throw new IllegalArgumentException("points is null");
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("point is null");
            }
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("dups");
            }
        }
    }

    private void addNode(Node node) {
        if (head == null) {
            head = last = node;
        }
        else {
            for (Node i = head; i != null; i = i.next) {
                if (i.segment.toString().equals(node.segment.toString())) {
                    return;
                }
            }
            last.next = node;
            last = node;
        }
        count++;
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        if (head == null) {
            return new LineSegment[0];
        }
        LineSegment[] ans = new LineSegment[count];
        Node node = this.head;
        for (int i = 0; i < ans.length; i++, node = node.next) {
            ans[i] = node.segment;
        }
        return ans;
    }

    private static class Node {
        private final LineSegment segment;
        private Node next;

        private Node(LineSegment segment) {
            this.segment = segment;
        }
    }

    public static void main(String[] args) {
        FastCollinearPoints points = new FastCollinearPoints(new Point[] {
                new Point(4, 4),
                new Point(2, 2),
                new Point(1, 1),
                new Point(3, 3),
                new Point(3, 31),
                });
        if (points.numberOfSegments() != 1) {
            throw new RuntimeException(String.format("count %s ans %s", points.numberOfSegments(),
                                                     Arrays.toString(points.segments())));
        }
        System.out.println(Arrays.toString(points.segments()));
        points = new FastCollinearPoints(new Point[] {
                new Point(4, 4),
                new Point(2, 2),
                new Point(1, 1),
                new Point(3, 3),
                new Point(14, 4),
                new Point(12, 2),
                new Point(11, 1),
                new Point(13, 3),
                });
        if (points.numberOfSegments() != 2) {
            throw new RuntimeException();
        }
        System.out.println(Arrays.toString(points.segments()));
        points = new FastCollinearPoints(new Point[] {
                new Point(4, 4),
                new Point(2, 2),
                new Point(1, 1),
                new Point(14, 4),
                new Point(12, 2),
                new Point(11, 1),
                new Point(13, 3),
                });
        if (points.numberOfSegments() != 1) {
            throw new RuntimeException();
        }
        System.out.println(Arrays.toString(points.segments()));
        points = new FastCollinearPoints(new Point[] {
                new Point(4, 4),
                new Point(2, 2),
                new Point(1, 1),
                new Point(14, 4),
                new Point(11, 1),
                new Point(13, 3),
                });
        if (points.numberOfSegments() != 0) {
            throw new RuntimeException();
        }
        System.out.println(Arrays.toString(points.segments()));
        points = new FastCollinearPoints(new Point[] {
                new Point(1, 1),
                new Point(2, 2),
                new Point(3, 3),
                new Point(4, 4),
                new Point(2, 4),
                new Point(4, 2),
                new Point(5, 1),
                });
        if (points.numberOfSegments() != 2) {
            throw new RuntimeException(Arrays.toString(points.segments()));
        }
        System.out.println(Arrays.toString(points.segments()));
        points = new FastCollinearPoints(new Point[] {
                new Point(1, 2),
                new Point(3, 2),
                new Point(2, 2),
                new Point(4, 2)
        });
        if (points.numberOfSegments() != 1) {
            throw new RuntimeException(Arrays.toString(points.segments()));
        }
        System.out.println(Arrays.toString(points.segments()));
    }
}
