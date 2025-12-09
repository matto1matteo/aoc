using System.Text.RegularExpressions;
using Microsoft.VisualBasic;

namespace AOC25.Problems;

public class Day06
    : Daily
{
    private readonly string fileName;

    public Day06(string fileName)
    {
        this.fileName = fileName;
    }
    public string FirstSolution()
    {
        using var reader = new StreamReader(File.OpenRead(fileName));
        var line = reader.ReadLine();

        var problem = new Math();
        while (line != null)
        {
            var input = CephalopodMathHelpers.ParseInput(line);
            if (input.Count() > 0) problem.Input.Add(input);
            else problem.Operations = CephalopodMathHelpers.ParseOperations(line);

            line = reader.ReadLine();
        }

        return problem.Result().ToString();
    }

    public string SecondSolution()
    {
        
        using var reader = new StreamReader(File.OpenRead(fileName));
        var line = reader.ReadLine();

        var problem = new CephalopodMath();
        while (line != null)
        {
            var input = CephalopodMathHelpers.ParseInputAsChar(line);
            if (input.Count() > 0) problem.Input.Add(input);
            else problem.Operations = line.ToList();

            line = reader.ReadLine();
        }

        return problem.Result().ToString();
    }

    enum OperationKind
    {
        Multiplication,
        Addition
    }

    class Math
    {
        public List<List<int>> Input { get; set; } = new();
        public List<List<char>> Matrix { get; set; } = new();
        public List<OperationKind> Operations { get; set; } = new();

        public long Result()
        {
            long result = 0;
            var m = Input.First().Count;
            var n = Input.Count;
            for (int i = 0; i < m; i++)
            {
                var kind = Operations[i];
                long partial = kind switch
                {
                    OperationKind.Multiplication => 1,
                    OperationKind.Addition => 0,
                    _ => throw new Exception("Unreachable")
                };
                for (int j = 0; j < n; j++)
                {
                    partial = kind switch
                    {
                        OperationKind.Multiplication => partial * Input[j][i],
                        OperationKind.Addition => partial + Input[j][i],
                        _ => throw new Exception("Unreachable")
                    };
                }
                result += partial;
            }
            return result;
        }
    }
    class CephalopodMath
    {
        public List<List<char>> Input { get; set; } = new();
        public List<char> Operations { get; set; } = new();

        public long Result()
        {
            long result = 0;
            var m = Input.First().Count;
            var n = Input.Count;
            var operands = new List<int>();
            for (int i = m - 1; i >= 0; i--)
            {
                OperationKind? kind = Operations[i] switch
                {
                    '*' => OperationKind.Multiplication,
                    '+' => OperationKind.Addition,
                    _ => null
                };
                var operand = 0;
                var exp = 0;
                for (int j = n - 1; j >= 0; j--)
                {
                    if (Input[j][i] == ' ') continue;
                    operand += (int)(Input[j][i] - 48)* (int)System.Math.Pow(10, exp);
                    exp++;
                }
                if (operand != 0) operands.Add(operand);
                if (kind is not null)
                {
                    long partial = kind == OperationKind.Multiplication ? 1 : 0;
                    partial = operands.Aggregate(partial, (acc, next) =>
                    {
                        if (kind == OperationKind.Multiplication) return acc * next;
                        return acc + next;
                    });
                    result += partial;
                    operands.Clear();
                }
            }
            return result;
        }

    }

    class CephalopodMathHelpers
    {
        public static List<int> ParseInput(string line)
        {
            var regex = new Regex("(\\d+)\\s*");
            var results = regex.Matches(line);
            if (results.Count == 0) return new();

            return results.Select(match =>
            {
                return int.Parse(match.ToString()!.Trim());
            }).ToList();
        }

        public static List<char> ParseInputAsChar(string line)
        {
            var regex = new Regex("((\\d+)\\s*)");
            var results = regex.Match(line);

            if (!results.Success) return new();
            
            return line.ToList();
        }

        public static List<OperationKind> ParseOperations(string line)
        {
            var regex = new Regex("([\\+|\\*]+)\\s\\s*");
            var results = regex.Matches(line);
            return results.Select(match =>
            {
                return match.ToString()!.Trim() switch
                {
                    "*" => OperationKind.Multiplication,
                    "+" => OperationKind.Addition,
                    _ => throw new Exception("Unreachable")
                };
            }).ToList();
        }
    }
}
