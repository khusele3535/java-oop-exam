#!/bin/bash

# ============================================================
# Java OOP Exam - Test Runner
# ============================================================

RED='\033[91m'
GREEN='\033[92m'
YELLOW='\033[93m'
CYAN='\033[96m'
BOLD='\033[1m'
RESET='\033[0m'
DIM='\033[2m'

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"
LIB_DIR="$ROOT_DIR/lib"

JUNIT_JAR="junit-platform-console-standalone-1.10.2.jar"
JUNIT_URL="https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.2/$JUNIT_JAR"

# ─── JUnit jar татах ───
download_junit() {
    if [ ! -f "$LIB_DIR/$JUNIT_JAR" ]; then
        echo -e "${CYAN}JUnit jar татаж байна...${RESET}"
        mkdir -p "$LIB_DIR"
        curl -sL "$JUNIT_URL" -o "$LIB_DIR/$JUNIT_JAR"
        if [ $? -ne 0 ]; then
            echo -e "${RED}JUnit jar татаж чадсангүй!${RESET}"
            exit 1
        fi
        echo -e "${GREEN}JUnit jar амжилттай татагдлаа.${RESET}"
    fi
}

# ─── Нэг даалгавар шалгах ───
run_assignment() {
    local name=$1
    local dir=$2
    local java_file=$3
    local test_file=$4

    echo ""
    echo -e "${BOLD}${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${RESET}"
    echo -e "${BOLD}${CYAN}  $name${RESET}"
    echo -e "${BOLD}${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${RESET}"

    local src="$ROOT_DIR/assignments/$dir/$java_file"
    local test="$ROOT_DIR/tests/$test_file"
    local build="$ROOT_DIR/build/$dir"

    mkdir -p "$build"

    # Compile
    echo -e "\n${DIM}[1/3] Компайл хийж байна...${RESET}"
    javac -d "$build" "$src" 2>&1
    if [ $? -ne 0 ]; then
        echo -e "${RED}  ✗ Компайл алдаатай!${RESET}"
        RESULTS+=("$name|COMPILE_ERROR|0|0")
        return 1
    fi
    echo -e "${GREEN}  ✓ Компайл амжилттай${RESET}"

    # Test
    echo -e "${DIM}[2/3] Тест ажиллуулж байна...${RESET}"
    javac -cp "$build:$LIB_DIR/$JUNIT_JAR" -d "$build" "$test" 2>&1
    if [ $? -ne 0 ]; then
        echo -e "${RED}  ✗ Тест компайл алдаатай!${RESET}"
        RESULTS+=("$name|TEST_COMPILE_ERROR|0|0")
        return 1
    fi

    local test_output
    test_output=$(java -jar "$LIB_DIR/$JUNIT_JAR" --class-path "$build" --select-class "${test_file%.java}" --disable-ansi-colors 2>&1)
    local test_exit=$?

    local passed=$(echo "$test_output" | grep -oP '\d+ tests successful' | grep -oP '\d+' || echo "0")
    local failed=$(echo "$test_output" | grep -oP '\d+ tests failed' | grep -oP '\d+' || echo "0")
    local total=$((passed + failed))

    if [ $test_exit -eq 0 ]; then
        echo -e "${GREEN}  ✓ Бүх тест амжилттай ($passed/$total)${RESET}"
    else
        echo -e "${YELLOW}  △ Тест: $passed/$total амжилттай${RESET}"
        echo "$test_output" | grep -E "✗|FAIL|AssertionError" | head -5 | while read -r line; do
            echo -e "${RED}    $line${RESET}"
        done
    fi

    RESULTS+=("$name|$( [ $test_exit -eq 0 ] && echo 'PASS' || echo 'PARTIAL')|$passed|$total")

    # AI Detection
    echo -e "${DIM}[3/3] AI Detection...${RESET}"
    python3 "$SCRIPT_DIR/ai_detector.py" "$src"
    local ai_exit=$?
    if [ $ai_exit -eq 1 ]; then
        AI_FLAGS+=("$name")
    fi

    return $test_exit
}

# ─── Main ───
main() {
    echo -e "${BOLD}${CYAN}"
    echo "  ╔═══════════════════════════════════════════╗"
    echo "  ║     🚀 Java OOP Exam - Test Runner 🚀     ║"
    echo "  ╚═══════════════════════════════════════════╝"
    echo -e "${RESET}"

    download_junit

    RESULTS=()
    AI_FLAGS=()
    OVERALL_EXIT=0

    local filter="${1:-all}"

    if [ "$filter" = "all" ] || [ "$filter" = "spaceship" ]; then
        run_assignment "Даалгавар 1: SpaceShip" "spaceship" "SpaceShip.java" "SpaceShipTest.java"
        [ $? -ne 0 ] && OVERALL_EXIT=1
    fi

    if [ "$filter" = "all" ] || [ "$filter" = "foodstation" ]; then
        run_assignment "Даалгавар 2: FoodStation" "foodstation" "FoodStation.java" "FoodStationTest.java"
        [ $? -ne 0 ] && OVERALL_EXIT=1
    fi

    if [ "$filter" = "all" ] || [ "$filter" = "commsystem" ]; then
        run_assignment "Даалгавар 3: CommSystem" "commsystem" "CommSystem.java" "CommSystemTest.java"
        [ $? -ne 0 ] && OVERALL_EXIT=1
    fi

    # Summary
    echo ""
    echo -e "${BOLD}${CYAN}╔═══════════════════════════════════════════════════════╗${RESET}"
    echo -e "${BOLD}${CYAN}║                    📊 НИЙТ ДҮН                       ║${RESET}"
    echo -e "${BOLD}${CYAN}╠═══════════════════════════════════════════════════════╣${RESET}"
    printf "${BOLD}${CYAN}║${RESET} %-25s │ %-8s │ %-12s ${BOLD}${CYAN}║${RESET}\n" "Даалгавар" "Статус" "Тест"
    echo -e "${BOLD}${CYAN}╠═══════════════════════════════════════════════════════╣${RESET}"

    for result in "${RESULTS[@]}"; do
        IFS='|' read -r name status passed total <<< "$result"
        case $status in
            PASS)
                status_str="${GREEN}✓ PASS${RESET}"
                test_str="${GREEN}$passed/$total${RESET}"
                ;;
            PARTIAL)
                status_str="${YELLOW}△ PARTIAL${RESET}"
                test_str="${YELLOW}$passed/$total${RESET}"
                ;;
            COMPILE_ERROR|TEST_COMPILE_ERROR)
                status_str="${RED}✗ ERROR${RESET}"
                test_str="${RED}—${RESET}"
                ;;
        esac
        printf "${BOLD}${CYAN}║${RESET} %-25s │ %-19s │ %-23s ${BOLD}${CYAN}║${RESET}\n" "$name" "$status_str" "$test_str"
    done

    echo -e "${BOLD}${CYAN}╚═══════════════════════════════════════════════════════╝${RESET}"

    if [ ${#AI_FLAGS[@]} -gt 0 ]; then
        echo ""
        echo -e "${RED}${BOLD}🚨 AI Detection анхааруулга:${RESET}"
        for flag in "${AI_FLAGS[@]}"; do
            echo -e "${RED}   - $flag: HIGH түвшний AI шинж тэмдэг!${RESET}"
        done
    fi

    echo ""
    exit $OVERALL_EXIT
}

main "$@"
