@echo off
echo Setting up Git repository for Dynamo Detailing...

echo.
echo Step 1: Initialize Git repository
git init

echo.
echo Step 2: Add all files to Git
git add .

echo.
echo Step 3: Create initial commit
git commit -m "Initial commit - Dynamo Detailing professional website with enhanced UI and complete customer management"

echo.
echo Step 4: Set main branch
git branch -M main

echo.
echo ===================================================
echo Git repository has been initialized!
echo.
echo Next steps:
echo 1. Create a new repository on GitHub.com
echo 2. Copy the repository URL
echo 3. Run: git remote add origin YOUR_GITHUB_URL
echo 4. Run: git push -u origin main
echo.
echo Then follow the DEPLOYMENT.md guide for free hosting!
echo ===================================================

pause
