(define (make-counter)
  (let ((count 0)) 
    (lambda () (set! count (+ count 1)) count)))

(define counter (make-counter))

(write (counter)) ; 1
(newline)

(write (counter)) ; 2
(newline)

(write (counter)) ; 3
(newline)
(exit)