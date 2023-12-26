public class TridiagonalMatrixSolver {

    public static double[] solve(double[] a, double[] b, double[] c, double[] d) {
        int n = d.length;

        double[] y = new double[n];
        double[] alpha = new double[n];
        double[] beta = new double[n];

        // Прямой ход
        alpha[0] = -c[0] / b[0];
        beta[0] = d[0] / b[0];
        for (int i = 1; i < n; i++) {
            double denominator = b[i] + a[i] * alpha[i - 1];
            alpha[i] = -c[i] / denominator;
            beta[i] = (d[i] - a[i] * beta[i - 1]) / denominator;
        }

        // Обратный ход
        y[n - 1] = beta[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            y[i] = alpha[i] * y[i + 1] + beta[i];
        }

        return y;
    }

    public static void main(String[] args) {
        // Пример использования
        double[] a = {0, 1, 1}; // Нижняя диагональ (a[0] не используется)
        double[] b = {4, 4, 4}; // Главная диагональ
        double[] c = {1, 1, 0}; // Верхняя диагональ (c[n-1] не используется)
        double[] d = {4, 6, 6}; // Столбец свободных членов

        double[] result = solve(a, b, c, d);

        for (double v : result) {
            System.out.println(v);
        }
    }
}
