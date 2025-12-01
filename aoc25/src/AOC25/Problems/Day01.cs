namespace AOC25.Problems
{
    class Day01
        : Daily, IDisposable
    {
        private enum Direction
        {
            L = -1,
            R = 1
        }

        private FileStream stream;
        private List<(Direction, int)> input = new();

        public Day01(string fileName)
        {
            stream = File.Open(fileName, FileMode.Open);
        }

        public void Dispose()
        {
            stream.Flush();
            stream.Close();
        }

        private void initProblemInput()
        {
            stream.Seek(0, SeekOrigin.Begin);
            var reader = new StreamReader(stream);
            var line = reader.ReadLine();
            while (line is not null)
            {
                var directionS = line[0];
                var direction = directionS switch
                {
                    'R' => Direction.R,
                    'L' => Direction.L,
                    _ => throw new Exception(),
                };

                var numberS = line.Substring(1);
                var number = int.Parse(numberS);
                input.Add((direction, number));
                line = reader.ReadLine();
            }

        }

        private static int resetClock(int v)
        {
            return v switch
            {
                < 0 => 100 + (v % 100),
                > 100 => v % 100,
                100 => 0,
                _ => v
            };
        }

        public string FirstSolution()
        {
            initProblemInput();

            var counter = 0;
            var partial = 50;

            foreach (var pair in input)
            {
                    var (direction, value) = pair;
                    if (direction == Direction.L)
                    {
                        value *= -1;
                    }
                    partial += value;

                    partial = partial % 100;

                    partial = resetClock(partial);

                    if (partial == 0)
                    {
                        counter++;
                    }
            }
            return counter.ToString();
        }

        private static int countBackward(int start, int clicks)
        {
            int counter = 0;
            for (int i = 0; i < Math.Abs(clicks); i++)
            {
                start--;
                if (start % 100 == 0) counter++;
            }
            return counter;
        }

        private static int countForward(int start, int clicks)
        {
            int counter = 0;
            for (int i = 0; i < clicks; i++)
            {
                start++;
                if (start % 100 == 0) counter++;
            }
            return counter;
        }

        public string SecondSolution()
        {
            if (!input.Any())
            {
                initProblemInput();
            }

            var counter = 0;
            var partial = 50;
            // Console.WriteLine("Starting at 50");

            foreach (var pair in input)
            {
                    var (direction, value) = pair;
                    if (direction == Direction.L)
                    {
                        value *= -1;
                    }

                    // Console.WriteLine($"Direction: {direction}, Value: {Math.Abs(value)}, New Partial: {partial}");

                    counter += (value < 0)
                        ? countBackward(partial, value)
                        : countForward(partial, value);

                    partial += value;
                    partial = resetClock(partial);

                    // Console.WriteLine($"Partial: {partial}, Counter: {counter}");
            }
            return counter.ToString();
        }
    }
}
