(define (make-integer-list start end)
  (if (> start end) ()
      (if (= start end) (list start)
	  (cons start (make-integer-list (+ start 1) end)))))

(define (fizzbuzz)
  (map (lambda (x)
	 (if (= (modulo x 15) 0) 'FizzBuzz
	     (if (= (modulo x 3) 0) 'Fizz
		 (if (= (modulo x 5) 0) 'Buzz x))))
       (make-integer-list 1 100)))

;; 1から100までのリストを得る.
;; (1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 ... 100)
(write "------------入力リスト------------")
(write (make-integer-list 1 100))
(newline)

;; 1から100までの整数について, 
;; 3の倍数はFizz, 5の倍数はBuzz, 15の倍数はFizzBuzzに置き換える.
;; (1 2 Fizz 4 Buzz Fizz 7 8 Fizz Buzz 11 Fizz 
;;  13 14 FizzBuzz 16 ... Buzzz)
(write "--------FizzBuzz置き換え--------")
(write (fizzbuzz))
(newline)
(exit)
