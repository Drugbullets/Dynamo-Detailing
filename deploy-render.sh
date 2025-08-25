#!/bin/bash
# Render.com Deployment Script for FREE hosting
# This uses H2 database (file-based) - completely free

echo "Building Dynamo Detailing for FREE hosting..."

# Build the application
mvn clean package -DskipTests

echo "Build complete! Ready for deployment."
echo ""
echo "Next steps:"
echo "1. Go to https://render.com"
echo "2. Sign up with GitHub (FREE)"
echo "3. Deploy from GitHub repository"
echo "4. Use these settings:"
echo "   - Build Command: mvn clean package -DskipTests"
echo "   - Start Command: java -Dspring.profiles.active=free -Dserver.port=\$PORT -jar target/detailing-1.0.0.jar"
echo ""
echo "Your website will be live at: https://your-app-name.onrender.com"
