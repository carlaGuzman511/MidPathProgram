# Notes App

### k6 Original Performance

C:\k6\k6.exe run test.js

PS C:\Users\Asus\source\repos\MidPathProgram\notesapp> C:\k6\k6.exe run test.js

         /\      Grafana   /‾‾/  
    /\  /  \     |\  __   /  /   
   /  \/    \    | |/ /  /   ‾‾\ 
  /          \   |   (  |  (‾)  |
 / __________ \  |_|\_\  \_____/ 

     execution: local
        script: test.js
        output: -

     scenarios: (100.00%) 1 scenario, 10 max VUs, 1m30s max duration (incl. graceful stop):
              * default: 10 looping VUs for 1m0s (gracefulStop: 30s)



  █ TOTAL RESULTS

    checks_total.......: 680     10.849717/s
    checks_succeeded...: 100.00% 680 out of 680
    checks_failed......: 0.00%   0 out of 680

    ✓ status is 200 or 201
    ✓ response is not empty

    HTTP
    http_req_duration..............: avg=340.2ms min=247.47ms med=338.28ms max=538.38ms p(90)=374.52ms p(95)=433.8ms
      { expected_response:true }...: avg=340.2ms min=247.47ms med=338.28ms max=538.38ms p(90)=374.52ms p(95)=433.8ms
    http_req_failed................: 0.00%  0 out of 340
    http_reqs......................: 340    5.424858/s

    EXECUTION
    iteration_duration.............: avg=3.68s   min=3.58s    med=3.68s    max=3.88s    p(90)=3.79s    p(95)=3.86s
    iterations.....................: 170    2.712429/s
    vus............................: 10     min=10       max=10
    vus_max........................: 10     min=10       max=10

    NETWORK
    data_received..................: 223 kB 3.6 kB/s
    data_sent......................: 80 kB  1.3 kB/s




running (1m02.7s), 00/10 VUs, 170 complete and 0 interrupted iterations
default ✓ [======================================] 10 VUs  1m0s

### ER Diagram Relational DB
![ER Diagram Relational DB](images/er-diagram-db.png)

### Import the following Postman Collection: NotesApp.postman_collection.json

![NotesApp.postman_collection.json](images/postman.png)

### Video Link

https://drive.google.com/file/d/1Em0lX-9hrMBaBphnKvP5BtvK-62eKq-7/view?usp=sharing
