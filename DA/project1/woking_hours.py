DEBUG_MODE = False


class Day:
    def __init__(self, min: int, max: int):
        self.min = min
        self.max = max
        self.work_hours = min

    def __str__(self):
        return str(self.min) + ' ' + str(self.work_hours) + ' ' + str(self.max)


def initial_values():
    [day_number, hours] = [int(i) for i in input().split()]
    days = []
    for i in range(day_number):
        [min, max] = [int(i) for i in input().split()]
        days.append(Day(min, max))
    return days, hours


def print_answer(days):
    print('Yes')
    for day in days:
        print(day.work_hours, end=' ')


def main():
    days, hours_remaining = initial_values()
    for day in days:
        if hours_remaining >= day.min:
            hours_remaining -= day.min
        else:
            debug(hours_remaining, days)
            raise Exception()

    for day in days:
        if hours_remaining == 0:
            break
        if (day.max - day.min) <= hours_remaining:
            hours_remaining -= day.max - day.min
            day.work_hours = day.max
        else:
            day.work_hours += hours_remaining
    if hours_remaining > 0:
        debug(hours_remaining, days)
        raise Exception()
    print_answer(days)


def debug(hours, days):
    if DEBUG_MODE:
        print(hours)
        for day in days:
            print(day)
    else:
        pass


if __name__ == '__main__':
    try:
        main()
    except Exception:
        print('No')
