
IS_DEBUG_MODE = False
class Cell:
    def __init__(self, value=None):
        self.value = value
        self.constraints = set()
        self.can_save = []

    def __str__(self):
        string = ''
        if IS_DEBUG_MODE:
            string = str(self.value) + ': '
            string += str(self.values_can_be())
        else:
            string += str(self.value)
        # for i in self.constraints:
        # string += str(i) + ', '
        return string

    def values_can_be(self):
        array = []
        for i in range(1, 10):
            if i not in self.constraints:
                array.append(i)
        self.can_save = array
        return array


class Map:
    def __init__(self, matrix):
        self.matrix = [[Cell(0) for _ in range(9)] for _ in range(9)]
        self._set_values(matrix)
        self.set_constraints()

    def set_constraints(self):
        for i in range(9):
            for j in range(9):
                value = self.matrix[i][j].value
                self._add_constraints(i, j, value)

    def add_value(self, i, j, value):
        self.matrix[i][j].value = value
        self._add_constraints(i, j, value)

    def _set_values(self, matrix: [[Cell]]):
        for i, row in enumerate(matrix):
            for j, col in enumerate(row):
                self.matrix[i][j] = Cell(col)

    def _add_constraints(self, i, j, value):
        self._add_row_constraints(i, value)
        self._add_col_constraints(j, value)
        self._add_square_constraints(i, j, value)

    def _add_row_constraints(self, row, value):
        for cell in self.matrix[row]:
            cell.constraints.add(value)

    def _add_col_constraints(self, col, value):
        for row in self.matrix:
            row[col].constraints.add(value)

    def _add_square_constraints(self, i, j, value):
        i = (i // 3) * 3
        j = (j // 3) * 3

        for row in range(i, i + 3):
            for col in range(j, j + 3):
                self.matrix[row][col].constraints.add(value)

    def __str__(self):
        string = ''
        for i in range(9):
            for j in range(9):
                string += str(self.matrix[i][j]) + ' '
            string += '\n'
        return string

    def is_solve(self):
        for row in self.matrix:
            for col in row:
                if col.value == 0:
                    return False
        return True


def get_inputs():
    map = []
    for i in range(9):
        map.append([int(i) for i in input().split()])
    return map


def normalize(map: Map):
    for i in range(9):
        for j in range(9):
            cell = map.matrix[i][j]
            if cell.value == 0 and len(cell.values_can_be()) == 1:
                cell.value = cell.can_save[0]
                map.add_value(i, j, cell.value)


if __name__ == '__main__':
    map = Map(get_inputs())
    while not map.is_solve():
        normalize(map)

    print(map)
