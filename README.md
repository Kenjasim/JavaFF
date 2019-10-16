# JavaFF Coursework Edition

This repo contains a clean and fresh copy of JavaFF for the 2019 AI Planning Module at King's College London.

## Getting set up 
1. Fork this repo! **don't clone it**. You will need your own copy on your repo for us to mark. To fork, click the fork button in the top right corner of [this repo](https://github.kcl.ac.uk/k1502498/JavaFF)

2. Once forked, clone your copy of the repo (make sure that your k number is at the start of the repo, not my one!)

3. Once cloned on your desktop, you can use the shell scripts provided to compile and run JavaFF
    - `./build.sh` will build JavaFF for you
    - `./run.sh <your-domain.pddl> <your-problem.pddl>` will run the built JavaFF on your domain and problem file (this will not work unless you've built first)

**Note: These scripts were written for the Informatics Lab machines, and may not work on your personal machines (and definitely won't work on Windows)**

4. Change the name in the `team_name.txt` file, to your team name. This will help us work out who is in what team

5. Commit all your changes before the deadline, 11th December, 2019, 11:59:59PM GMT. Any changes committed after this time will be ignored (we will use the commit closest to this time that is not over)

# Rules
- Do not change the build shell scripts, or the server-build shell script, these are the scripts we will run when we test your code. We will conduct these tests on the lab machines, so if you run the build script on your code on the lab machine and it doesn't compile, you know it won't compile for us
- Do not submit compiled files. This repo has been setup to ignore files compiled with the build script, but if you compile using the command line (`javac`) this might accidentally get committed. **We will not test pre-compiled files and will wipe them before testing**
- Make sure your team name is spelt correctly when you enter it into the team_name.txt file
- We will compile and run all submission on an informatics lab machine, it up to you to ensure your code base compiles by submission time!