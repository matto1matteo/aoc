namespace AOC25.Problems;

public class PackOf2
{
    public int First { get; set; }
    public int Second { get; set; }

    public int Total { get => (First * 10) + Second; }

    public override string ToString()
    {
        return $"Pack {{ First: {First}, Second: {Second}, Total: {Total}}}";
    }
}

public class PackOfN(int size)
{
    public readonly int[] Batteries = new int[size];

    public long Total { get => Sum(); }
    private long Sum()
    {
        long t = 0;
        for (int i = 0; i < Batteries.Length; i++)
        {
            long zeroes = (long)Math.Pow(10, Batteries.Length - i - 1);
            var partial =  zeroes * Batteries[i];
            t += partial;
        }
        return t;
    }

    public override string ToString()
    {
        return $"PackOfN {{ Batteries: [ {string.Join(", ", Batteries.ToCharArray())} ], Total: {Total} }}";
    }
}

public class Bank(string bank)
{
    private readonly int[] bank = bank.ToIntArray();

    public PackOf2 GreaterPack()
    {
        var firstMaxIndex = bank[..(bank.Length-1)].MaxIndex();
        var secondMaxIndex = bank[(firstMaxIndex+1)..].MaxIndex();
        return new()
        {
            First = bank[firstMaxIndex],
            Second = bank[secondMaxIndex + firstMaxIndex + 1]
        };
    }

    public PackOfN GreaterPack(int n)
    {
        var pack = new PackOfN(n);
        var indices = new int[n];
        
        var precIndex = 0;
        var i = 0;
        var start = 0;
        var end = bank.Length - (n - (i + 1));
        var slice = bank[start..end];
        indices[i] = slice.MaxIndex() + precIndex;
        pack.Batteries[0] = bank[indices[0]];
        precIndex = indices[i];
        i++;

        while (i < n)
        {
            start = indices[i-1] + 1;
            end = bank.Length - (n - (i + 1));
            slice = bank[start..end];

            var localIndex = slice.MaxIndex();
            indices[i] = localIndex + precIndex + 1;

            var resultingIndex = indices[i];
            pack.Batteries[i] = bank[resultingIndex];

            precIndex = indices[i];
            i++;
        }

        return pack;
    }
}

public class Day03(string fileName)
        : Daily
{
    private readonly string fileName = fileName;

    public string FirstSolution()
    {
        using var reader = new StreamReader(File.OpenRead(fileName));

        var joltages = new List<long>();

        var batteryPack = reader.ReadLine();
        while(batteryPack is not null)
        {
            var bank = new Bank(batteryPack);
            var pack = bank.GreaterPack();
            joltages.Add(pack.Total);
            batteryPack = reader.ReadLine();
        }
        return joltages.Sum().ToString();
    }

    public string SecondSolution()
    {
        using var reader = new StreamReader(File.OpenRead(fileName));

        var joltages = new List<long>();

        var batteryPack = reader.ReadLine();
        while(batteryPack is not null)
        {
            var bank = new Bank(batteryPack);
            var pack = bank.GreaterPack(12);
            joltages.Add(pack.Total);
            batteryPack = reader.ReadLine();
        }
        return joltages.Sum().ToString();
    }
}

public static class Extensions
{
    public static char[] ToCharArray(this IEnumerable<int> collection)
    {
        return [..collection.Select(c => (char)(c + 48))];
    }

    public static int[] ToIntArray(this string s)
    {
        return [..s.Select(c => c - 48)];
    }

    public static int MaxIndex(this int[] collection)
    {
        var i = 0;
        int value = -1;
        int temp = i;
        while(i < collection.Length)
        {
            var x = collection[i];
            if (x > value)
            {
                value = x;
                temp = i;
            }
            i++;
        }
        return temp;
    }
}
