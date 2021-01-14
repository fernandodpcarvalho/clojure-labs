(ns hospital-04.logic-test
  (:require [clojure.test :refer :all]
            [hospital-04.logic :refer :all]))

(deftest cabe-na-fila?-test

  ;borda do zero
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera []}, :espera)))

  ;borda do limite
  (testing "Que não cabe na fila quando a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]}, :espera))))

  ;one off da borda do limite para cima
  (testing "Que não cabe na fila quando tem mais do que uma fila cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

  ;dentro das bordas
  (testing "Que cabe na fila quando tem pouco menos do que uma fila cheia"
    (is (cabe-na-fila? {:espera [1 2 3 4]}, :espera))
    (is (cabe-na-fila? {:espera [1 2]}, :espera)))

  (testing "Que acontece quando departamento não existe?"
    (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x))))

  (testing "Que acontece quando departamento não existe?"
    (is (not (cabe-na-fila-02? {:espera [1 2 3 4]}, :raio-x))))

  (testing "Que acontece quando departamento não existe?"
    (is (not (cabe-na-fila-03? {:espera [1 2 3 4]}, :raio-x)))))
