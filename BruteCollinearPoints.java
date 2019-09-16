/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {

    private Node head, last;
    private int count;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        Point[] aux = new Point[4];

        for (int i = 0; i < points.length - 3; i++) {
            aux[0] = points[i];
            for (int j = i + 1; j < points.length - 2; j++) {
                aux[1] = points[j];
                for (int k = j + 1; k < points.length - 1; k++) {
                    aux[2] = points[k];
                    for (int l = k + 1; l < points.length; l++) {
                        aux[3] = points[l];
                        if (aux[0].slopeTo(aux[1]) == aux[0].slopeTo(aux[2])
                                && aux[0].slopeTo(aux[1]) == aux[0].slopeTo(aux[3])) {
                            Arrays.sort(aux);
                            if (head == null) {
                                head = new Node(new LineSegment(aux[0], aux[3]));
                                last = head;
                            }
                            else {
                                final Node node = new Node(new LineSegment(aux[0], aux[3]));
                                last.next = node;
                                last = node;
                            }
                            count++;
                        }
                    }
                }
            }
        }

    }

    private static class Node {
        final LineSegment segment;
        Node next;

        private Node(LineSegment segment) {
            this.segment = segment;
        }
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
