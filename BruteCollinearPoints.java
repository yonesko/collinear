/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] segments;
    private int count;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        segments = new LineSegment[points.length / 4];
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
                            segments[count++] = new LineSegment(aux[0], aux[3]);
                        }
                    }
                }
            }
        }

    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, count);
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
    }
}
