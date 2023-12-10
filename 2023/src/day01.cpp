#include <aoc/aoc.hpp>
#include <iostream>
#include <istream>
#include <string>
#include <aoc/daily.hpp>

namespace mtt {

Day01::Day01(std::istream& stream)
{
    std::string line;
    while (std::getline(stream, line)) {
        data.push_back(line);
    }
}

void Day01::printSolution()
{
    std::cout << "Solution first problem: " << firstSolution() << "\n";
    std::cout << "Solution second problem: " << secondSolution() << "\n";
}

bool isDigit(const char c)
{
    return c >= 48 && c <= 57;
}

bool startsWith(const std::string & s, const std::string compare)
{
    if (s.length() < compare.length())
    {
        return false;
    }

    for (size_t i = 0; i < compare.length(); i++) {
        if (s[i] != compare[i])
        {
            return false;
        }
    }
    return true;
}

bool isSpelled(const std::string & s)
{
    return startsWith(s, "one")
        || startsWith(s, "two")
        || startsWith(s, "three")
        || startsWith(s, "four")
        || startsWith(s, "five")
        || startsWith(s, "six")
        || startsWith(s, "seven")
        || startsWith(s, "eight")
        || startsWith(s, "nine")
        || startsWith(s, "zero");
}

char fromSpelled(const std::string & s)
{
    if(startsWith(s, "one"))
        return '1';
    if(startsWith(s, "two"))
        return '2';
    if(startsWith(s, "three"))
        return '3';
    if(startsWith(s, "four"))
        return '4';
    if(startsWith(s, "five"))
        return '5';
    if(startsWith(s, "six"))
        return '6';
    if(startsWith(s, "seven"))
        return '7';
    if(startsWith(s, "eight"))
        return '8';
    if(startsWith(s, "nine"))
        return '9';
    if(startsWith(s, "zero"))
        return '0';

    throw "invalid string";
}

char firstDigit(const std::string& s)
{
    char d = 0;
    for(auto c : s) {
        if (isDigit(c))
        {
            return c;
        }
    }
    return d;
}

char lastDigit(const std::string& s)
{
    for (size_t i = s.length() - 1; i >= 0; i--) {
        if (isDigit(s[i]))
        {
            return s[i];
        }
    }

    return 0;
}

std::string Day01::firstSolution()
{
    int result = 0;
    char first = 0, second = 0;
    for (auto cord : data) {
        first = firstDigit(cord);
        second = lastDigit(cord);
        result += ((int)first - 48) * 10;
        result += (int)second - 48;
    }
    return "First solution is: " + std::to_string(result);
}

char firstDigit2(const std::string & s)
{
    char d = 0;
    char c = 0;
    for(size_t i = 0; i < s.length(); i++) {
        c = s[i];
        if (isDigit(c))
        {
            return c;
        }
        if (isSpelled(s.substr(i)))
        {
            return fromSpelled(s.substr(i));
        }
    }
    return d;
}

char lastDigit2(const std::string & s)
{
    for (size_t i = s.length() - 1; i >= 0; i--) {
        if (isDigit(s[i]))
        {
            return s[i];
        }
        if (isSpelled(s.substr(i)))
        {
            return fromSpelled(s.substr(i));
        }
    }

    return 0;
}

std::string Day01::secondSolution()
{
    int result = 0;
    char first = 0, second = 0;
    for (auto cord : data) {
        first = firstDigit2(cord);
        second = lastDigit2(cord);
        result += ((int)first - 48) * 10;
        result += (int)second - 48;
    }
    return "Second solution is: " + std::to_string(result);
}

}
