DEBUG_MODE = False


class Day:
    def __init__(self, min: int, max: int):
        self.min = min
        self.max = max
        self.work_hours = min

    def __str__(self):
        return str(self.min) + ' ' + str(self.work_hours) + ' ' + str(self.max)


def debug(hours, days):
    if DEBUG_MODE:
        print(hours)
        for day in days:
            print(day)
    else:
        pass


def print_answer(days):
    print('YES')
    for day in days:
        print(day.work_hours, end=' ')


def initial_values():
    [day_number, hours] = [int(i) for i in input().split()]
    days = []
    min_sum = max_sum = 0
    for i in range(day_number):
        [min, max] = [int(i) for i in input().split()]
        days.append(Day(min, max))
        min_sum += min
        max_sum += max
    if not (min_sum <= hours <= max_sum):
        raise Exception
    return days, hours - min_sum


def main():
    days, hours_remaining = initial_values()
    for day in days:
        if (day.max - day.min) <= hours_remaining:
            hours_remaining -= (day.max - day.min)
            day.work_hours = day.max
        else:
            day.work_hours += hours_remaining
            hours_remaining = 0
    print_answer(days)


try:
    main()
except Exception:
    print('NO')
