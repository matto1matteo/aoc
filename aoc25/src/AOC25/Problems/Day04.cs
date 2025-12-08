using System.Text;

namespace AOC25.Problems;

public class Day04(string fileName)
    : Daily
{
    private readonly string fileName = fileName;
    public string FirstSolution()
    {
        using var reader = new StreamReader(File.OpenRead(fileName));
        var line = reader.ReadLine();
        var grid = new Grid();
        int i = 0;
        while (line != null)
        {
            grid.Data.Add(Grid.ParseLine(line));
            line = reader.ReadLine();
            i++;
        }

        var removable = grid.Data.Select((y, i) =>
        {
            return y
                .Select((_, j) => { return grid.CanBeFreed(j, i);});
        })
        .SelectMany(x => x)
        .Select(x => x ? 1 : 0)
        .Sum();

        return removable.ToString();
    }

    public string SecondSolution()
    {
        using var reader = new StreamReader(File.OpenRead(fileName));
        var line = reader.ReadLine();
        var grid = new Grid();
        int i = 0;
        while (line != null)
        {
            grid.Data.Add(Grid.ParseLine(line));
            line = reader.ReadLine();
            i++;
        }

        var temp = grid.Remove();
        var removed = temp;
        while (temp > 0)
        {
            temp = grid.Remove();
            removed += temp;
        }


        return removed.ToString();
    }
}

public class Cell
{
    public required CellContent Content { get; set; }

    public void Free()
    {
        Content = new Freed();
    }
}

public class Grid
{
    public List<List<Cell>> Data { get; set; } = new();

    public static List<Cell> ParseLine(string line)
    {
        var gridLine = new List<Cell>();
        foreach (var item in line)
        {
            gridLine.Add(item switch
            {
                '.' => new Cell { Content = CellContent.Empty() },
                '@' => new Cell { Content = CellContent.Occupied() },
                _ => throw new Exception("Unreachable")
            });
        }
        return gridLine;
    }

    public int Remove()
    {
        var result = 0;
        for (int i = 0; i < Data.Count(); i++)
        {
            for (int j = 0; j < Data[i].Count(); j++)
            {
                if (CanBeFreed(j, i))
                {
                    Data[i][j].Free();
                    result++;
                }
            }
        }
        return result;
    }

    private bool HasCell(int x, int y) =>
        x >= 0 && x < Data.Count() && y >= 0 && y < Data.First().Count();
    
    public bool CanBeFreed(int x, int y)
    {
        if (Data[y][x].Content is Empty || Data[y][x].Content is Freed) return false;
        var obstacleCount = 0;
        var freeCount = 0;
        for (int i = -1; i <= 1; i++)
        {
            for (int j = -1; j <= 1; j++)
            {
                // System.Console.WriteLine($"i: {i}, j: {j}, x: {x}, y: {y}, checking Grid[{y + j}][{x + i}]");
                if (i == j && i == 0) continue;
                if (!HasCell(x + i, y + j)) freeCount++;
                else
                {
                    var cell = Data[y + j][x + i].Content;
                    if (cell is Empty || cell is Freed) freeCount++;
                    else if (cell is Occupied) obstacleCount++;
                }

                if (obstacleCount > 3) return false;
                if (freeCount >= 5) return true;
            }
        }
        return false;
    }

    public override string ToString()
    {
        var builder = new StringBuilder();
        for (int i = 0; i < Data.Count(); i++)
        {
            builder.Append(string.Join(" ", Data[i].Select(x => x.Content.Value)));
            builder.Append('\n');
        }
        return builder.ToString();
    }
}


public record CellContent(string Value)
{
    public static CellContent Empty() => new Empty();
    public static CellContent Occupied() => new Occupied();
};

public sealed record Empty() : CellContent(".");

public sealed record Occupied() : CellContent("@");

public sealed record Freed(): CellContent("x");
