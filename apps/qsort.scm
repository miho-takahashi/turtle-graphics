(define (filter pred? lst)
  (if (null? lst) lst
      (if (pred? (car lst))
	  (cons (car lst) (filter pred? (cdr lst)))
	  (filter pred? (cdr lst)))))

(define (qsort pred? lst)
  (if (null? lst) lst
      (let ((pivot (car lst))
	    (rest (cdr lst)))
	(append 
	 (qsort pred? (filter (lambda (x) (pred? x pivot)) rest))
	 (list pivot)
	 (qsort pred? (filter (lambda (x) (not (pred? x pivot))) rest))))))

;; 5未満を抽出する
;; (2 3 4 4 1)
(write (filter (lambda (x) (< x 5)) '(8 5 2 7 3 4 4 10 1)))
(newline)

;; 昇順で並べ替える
;; (1 2 3 4 4 5 7 8 10)
(write "--------昇順--------")
(write '(8 5 2 7 3 4 4 10 1))
(write "Execute Quicksort.")
(write (qsort < '(8 5 2 7 3 4 4 10 1)))
(newline)

;; 降順で並べ替える
;; (10 8 7 5 4 4 3 2 1)
(write "--------降順--------")
(write '(8 5 2 7 3 4 4 10 1))
(write "Execute Quicksort.")
(write (qsort > '(8 5 2 7 3 4 4 10 1)))
(newline)
(exit)