;;1
(define PI 3.1415926)
(define arg 0)
(define colorNumber 0)


;;2
(define tx 0)
(define ty 0)
(define direction 0)
(define pendown #f)

;;3
(define (pen x)
  (if (eq? x 'down) (set! pendown #t) (set! pendown #f)))
(define (colorjudge Number)
(set! colorNumber (mod Number 4)))

;;4
(define (forward distance)
  (let ((nx (+ tx (* distance (cos (/ (* PI direction) 180)))))
	(ny (+ ty (* distance (sin (/ (* PI direction) 180))))))
    (if pendown (draw-line tx ty nx ny))
    (set! tx nx)
    (set! ty ny)))

;;5
(define (left angle)
  (set! direction (+ direction angle)))
(define (right angle)
  (set! direction (- direction angle)))

;;6
(define (draw-line sx sy ex ey)
  (display sx) (display " ") (display sy) (newline)
  (display ex) (display " ") (display ey) (newline) 
  (newline))

;;7
(define (make-args node)
  (set! arg (- 180 (/ 180 node)))) 
(define (draw-star node length arg)
  (forward length)
  (right arg)
  (if (not(= node 1))
      (draw-star (- node 1) length arg)
      )
  )

;;8
(define (star node length Number)
  (colorjudge Number)
  (write colorNumber)
  (newline)
  (make-args node)
  (pen 'down) 
  (draw-star node length arg)
  )


(star 5 100 1)
