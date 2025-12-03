using System.Globalization;
using AOC25.Problems;

var basePath = Environment.GetEnvironmentVariable("AOC25_BASE_PATH");
var problems = new List<Daily>
{
    new Day01(Path.Join(basePath, "day01.txt")),
    new Day02(Path.Join(basePath, "day02.txt")),
    new Day03(Path.Join(basePath, "day03.txt")),
};

Console.WriteLine($"Advent of code 2025");
for (int i = 1; i <= problems.Count; i++)
{
    var problem = problems[i - 1];
    var date = new DateTime(2025, 12, i);
    Console.WriteLine();
    Console.WriteLine("---------------------------------------------------------------");
    Console.WriteLine($"Problem of the day: {date.ToString("d MMMM", CultureInfo.CreateSpecificCulture("en-US"))}");
    Console.WriteLine($"The solution to the first half of the problem is: {problem.FirstSolution()}");
    Console.WriteLine($"The solution to the second half of the problem is: {problem.SecondSolution()}");
    Console.WriteLine("---------------------------------------------------------------");
}
