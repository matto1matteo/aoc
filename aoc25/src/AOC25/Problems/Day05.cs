namespace AOC25.Problems;

public class Day05
    : Daily
{
    private readonly string fileName;

    public Day05(string fileName)
    {
        this.fileName = fileName;
    }

    public string FirstSolution()
    {
        using var reader = new StreamReader(File.OpenRead(fileName));

        var line = reader.ReadLine();
        var ranges = new List<(long, long)>();

        while (line is not null && line != "")
        {
            var range = line.Split('-');
            ranges.Add((long.Parse(range[0]), long.Parse(range[1])));
            line = reader.ReadLine();
        }

        line = reader.ReadLine();
        var fresh = 0;
        while (line is not null)
        {
            var id = long.Parse(line);
            var isFresh = ranges.Any(t =>
            {
                return (id >= t.Item1 && id <= t.Item2);
            });
            if (isFresh) fresh++;
            line = reader.ReadLine();
        }
        return fresh.ToString();
    }

    public string SecondSolution()
    {
        using var reader = new StreamReader(File.OpenRead(fileName));

        var line = reader.ReadLine();
        var ranges = new List<Range>();

        // add ranges
        while (line is not null && line != "")
        {
            var range = line.Split('-');
            var start = long.Parse(range[0]);
            var end = long.Parse(range[1]);

            Range r = new() {Start = start, End = end};

            var intersections = ranges.Where(x => x.Intersect(r) != Range.IntersectKind.None);
            Range corrected = r;
            foreach (var intersection in intersections)
            {
                corrected = corrected.ResolveIntersection(intersection);
            }

            ranges.RemoveAll(x => intersections.Contains(x));
            ranges.Add(corrected);
            
            line = reader.ReadLine();
        }


        long partial = 0;
        // Compute ranges
        foreach (var range in ranges)
        {
            partial += (range.End - range.Start) + 1;
        }

        return partial.ToString();
    }

    
    class Range
    {
        public long Start { get; set; }
        public long End { get; set; }

        private bool IntersectStart(Range other)
        {
            return other.Start >= Start && other.Start <= End;
        }

        private bool IntersectEnd(Range other)
        {
            return other.End >= Start && other.End <= End;
        }

        
        /// <summary>
        /// Tell whether how `other` range intersect with this.
        /// </summary>
        /// <param name="other"></param>
        /// <returns></returns>
        public IntersectKind Intersect(Range other)
        {
            if (IntersectStart(other) && IntersectEnd(other)) return IntersectKind.Inclusive;
            if (other.IntersectStart(this) && other.IntersectEnd(this)) return IntersectKind.Inclusive;
            if (IntersectStart(other)) return IntersectKind.Start;
            if (IntersectEnd(other)) return IntersectKind.End;
            return IntersectKind.None;
        }

        public Range ResolveIntersection(Range other)
        {
            var kind = Intersect(other);
            return kind switch
            {
                IntersectKind.Inclusive => new()
                {
                    Start = Math.Min(Start, other.Start),
                    End = Math.Max(End, other.End)
                },
                IntersectKind.Start => new()
                {
                    Start = Start,
                    End = other.End
                },
                IntersectKind.End => new()
                {
                    Start = other.Start,
                    End = End
                }, 
                _ => throw new Exception("Unreachable")
            };
        }

        public enum IntersectKind
        {
            None,
            Start,
            End,
            Inclusive
        }
    }
}
