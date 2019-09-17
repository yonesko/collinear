/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class FastCollinearPoints {
    private Node head, last;
    private int count;
    private LineSegment[] lineSegmentsI;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("points is null");
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("point is null");
            }
        }
        points = Arrays.copyOf(points, points.length);
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
                    addNode(new Node(ls[0], ls[ls.length - 1]));
                    l = -1;
                }
                else {
                    l = -1;
                }
            }
        }
    }

    private void validate(Point[] points) {
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
            last.next = node;
            last = node;
        }
        count++;
    }

    public int numberOfSegments() {
        return segments().length;
    }

    public LineSegment[] segments() {
        if (lineSegmentsI == null) {
            lineSegmentsI = initSegments();
            head = last = null;
        }
        return Arrays.copyOf(lineSegmentsI, lineSegmentsI.length);
    }

    private LineSegment[] initSegments() {
        if (head == null) {
            return new LineSegment[0];
        }
        Node[] ans = new Node[count];
        int distinctCount = 0;
        Node node = this.head;
        for (int i = 0; i < ans.length; i++, node = node.next) {
            if (!segmentExists(ans, node.p, node.q)) {
                ans[i] = node;
                distinctCount++;
            }
        }
        LineSegment[] seg = new LineSegment[distinctCount];
        int i = 0;
        for (Node an : ans) {
            if (an != null) {
                seg[i++] = an.segment();
            }
        }
        return seg;
    }

    private boolean segmentExists(Node[] ans, Point p, Point q) {
        for (Node an : ans) {
            if (an != null && an.q.compareTo(q) == 0 && an.p.compareTo(p) == 0) {
                return true;
            }
        }
        return false;
    }

    private static class Node {
        private final Point p, q;
        private Node next;

        private Node(Point p, Point q) {
            this.p = p;
            this.q = q;
        }

        LineSegment segment() {
            return new LineSegment(p, q);
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
