namespace AOC25.Problems;

public class Day02(string fileName)
    : Daily
{
    private readonly string fileName = fileName;

    public string FirstSolution()
    {
        using var stream = File.OpenRead(fileName);
        using var reader = new StreamReader(stream);
        var invalidIds = new List<long>();
        foreach (var idRange in reader.ReadUntil(','))
        {
            var range = idRange.Split('-');
            var start = long.Parse(range[0]);
            var end = long.Parse(range[1]);
            var count = end - start + 1;
            var newIds = LongEnumerable
                .Range(start, count)
                .Where(n => IsInvalid(n.ToString()));

            invalidIds.AddRange(newIds);
        }

        return invalidIds.Sum().ToString();
    }

    private static bool IsInvalid(string n)
    {
        if (n.Length % 2 != 0) return false;
        var half = n.Length / 2;

        var first = n[..half];
        var second = n[half..];
        return first == second;
    }

    private static bool IsInvalidNth(string n, int count)
    {
        if (n.Length % count != 0) return false;
        var half = n.Length / count;

        var halves = Enumerable.Range(0, count)
            .Select(i => n[(half*i)..(half*(i+1))]);
        return !halves.Any(x => x != halves.First());
    }

    public string SecondSolution()
    {
        using var stream = File.OpenRead(fileName);
        using var reader = new StreamReader(stream);
        var invalidIds = new List<long>();
        foreach (var idRange in reader.ReadUntil(','))
        {
            var range = idRange.Split('-');
            var start = long.Parse(range[0]);
            var end = long.Parse(range[1]);
            var count = end - start + 1;
            var newIds = LongEnumerable
                .Range(start, count)
                .Where(n => {
                    var s = n.ToString();
                    return IsInvalidNth(s, 2) || IsInvalidNth(s, 3) || IsInvalidNth(s, 5) || IsInvalidNth(s, 7);
                });

            invalidIds.AddRange(newIds);
        }

        return invalidIds.Sum().ToString();
    }
}

public static class StreamReaderExtensions
{
    public static IEnumerable<string> ReadUntil(this StreamReader stream, char delimiter)
    {
        List<char> chars = [];

        while(stream.Peek() >= 0)
        {
            var c = (char)stream.Read();
            if (c == delimiter)
            {
                yield return new string([.. chars]);
                chars.Clear();
                continue;
            }

            chars.Add(c);
        }

        yield return new string([..chars]);
    }
}

public static class LongEnumerable
{
    public static IEnumerable<long> Range(long start, long count)
    {
        for (long i = 0; i <= count; i++)
        {
            yield return start + i;
        }
    }
}
