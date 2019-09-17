/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {

    private Node head, last;
    private int count;
    private LineSegment[] lineSegmentsI;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("points is null");
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("point is null");
            }
        }
        points = Arrays.copyOf(points, points.length);
        validate(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double ijSlope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    double ikSlope = points[i].slopeTo(points[k]);
                    if (ijSlope == ikSlope) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (ijSlope == ikSlope && ijSlope == points[i].slopeTo(points[l])) {
                                Point[] aux = { points[i], points[j], points[k], points[l] };
                                Arrays.sort(aux);
                                addNode(new Node(new LineSegment(aux[0], aux[3])));
                            }
                        }
                    }
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

    private static class Node {
        private final LineSegment segment;
        private Node next;

        private Node(LineSegment segment) {
            this.segment = segment;
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        if (lineSegmentsI == null) {
            lineSegmentsI = getLineSegmentsI();
            head = last = null;
        }
        return Arrays.copyOf(lineSegmentsI, lineSegmentsI.length);
    }

    private LineSegment[] getLineSegmentsI() {
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

    public static void main(String[] args) {
        BruteCollinearPoints points = new BruteCollinearPoints(new Point[] {
                new Point(4, 4),
                new Point(2, 2),
                new Point(1, 1),
                new Point(3, 3),
                new Point(3, 31),
                });
        if (points.numberOfSegments() != 1) {
            throw new RuntimeException();
        }
        System.out.println(Arrays.toString(points.segments()));
        points = new BruteCollinearPoints(new Point[] {
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
        points = new BruteCollinearPoints(new Point[] {
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
        points = new BruteCollinearPoints(new Point[] {
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
        points = new BruteCollinearPoints(new Point[] {
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
        points = new BruteCollinearPoints(new Point[] {
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
