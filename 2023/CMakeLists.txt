cmake_minimum_required(VERSION 3.20)

project(aoc_2023
    VERSION 1.0.0
    DESCRIPTION "2023 AOC"
    LANGUAGES CXX
)

set(CMAKE_EXPORT_COMPILE_COMMANDS ON)

set(BASE_PATH "/home/mattomatteo/Documenti/aoc")
configure_file("${CMAKE_CURRENT_SOURCE_DIR}/includes/env.hpp.in"
    "${CMAKE_CURRENT_BINARY_DIR}/includes/env.hpp"
    @ONLY
)

set(main_sources
    "src/main.cpp"
)

set(aoc_sources
    "src/aoc.cpp"
)

set(aoc_daily
    "src/day01.cpp"
    "src/day02.cpp"
)

set(aoc_headers
    "includes/aoc"
    "${CMAKE_CURRENT_BINARY_DIR}/includes"
)

add_library(aoc)
target_include_directories(aoc
    PUBLIC ${aoc_headers}
)
target_sources(aoc
    PUBLIC ${aoc_sources}
    PRIVATE ${aoc_daily}
)

add_executable(${CMAKE_PROJECT_NAME}
    ${main_sources}
)
target_link_libraries(${CMAKE_PROJECT_NAME}
    aoc
)
