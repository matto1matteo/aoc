cmake_minimum_required(VERSION 3.20)

project(aoc_2015
    VERSION 1.0.0
    DESCRIPTION "2023 AOC"
    LANGUAGES CXX
)


set(sources
    "src/main.cpp"
)

add_executable(${CMAKE_PROJECT_NAME}
    ${sources}
)
