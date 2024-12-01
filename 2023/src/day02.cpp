#include <aoc/aoc.hpp>
#include <iostream>
#include <istream>
#include <aoc/daily.hpp>
#include <sstream>
#include <string>
#include <vector>

namespace mtt {

Extraction Extraction::parseLine(std::string line)
{
    Extraction e;
    int value = 0;
    std::string color;
    std::stringstream stream(line);
    while (stream >> value) {
        std::getline(stream, color, ',');

        if (color == " green") {
            e.Green = value;
        } else if (color == " blue") {
            e.Blue = value;
        } else if (color == " red") {
            e.Red = value;
        } else {
            throw "Invalid color found: " + color;
        }
    }
    return e;
}

Game Game::parseLine(std::string line)
{
    Game g;
    std::string s;
    std::stringstream stream(line);
    stream >> s;

    if (s != "Game") {
        throw "Invalid line";
    }

    std::getline(stream, s, ':');
    g.Id = std::stoi(s);

    while (std::getline(stream, s, ';')) {
        Extraction ex = Extraction::parseLine(s);
        g.Extractions.push_back(ex);
    }
    return g;
}

Day02::Day02(std::istream& s)
    : DailyProblem(2)
{
    std::string line;
    while (std::getline(s, line)) {
        auto game = Game::parseLine(line);
        games.push_back(game);
    }
}

void Day02::printSolution()
{
    std::cout << "Solution to day " << getDay() << "\n";
    std::cout << "Solution to first problem: " << firstSolution() << "\n";
    std::cout << "Solution to second problem: " << secondSolution() << "\n";
}

bool gameValid1(const Game& game)
{
    for (auto ex : game.Extractions) {
        if (ex.Red > 12 || ex.Green > 13 || ex.Blue > 14) {
            return false;
        }
    }
    return true;
}

std::string Day02::firstSolution()
{
    int value = 0;
    for (auto game : games) {
        if (gameValid1(game)) {
            value += game.Id;
        }
    }
    return std::to_string(value);
}
std::string Day02::secondSolution()
{
    return "Not implemented yet";
}

}
