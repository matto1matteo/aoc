export AOC25_BASE_PATH = $(shell pwd)/src/AOC25/Resources
.PHONY: all
all: build run

.PHONY: build
build:
	dotnet build

.PHONY: run
run:
	dotnet src/AOC25/bin/Debug/net9.0/AOC25.dll

.SILENT:
.PHONY: clean
clean:
	dotnet clean
	rm -r src/AOC25/bin 2> /dev/null || true
	rm -r src/AOC25/obj 2> /dev/null || true
