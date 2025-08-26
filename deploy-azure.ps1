# Azure App Service Deployment Script
# Run this in PowerShell after installing Azure CLI

Write-Host "🚀 Deploying Dynamo Detailing to Azure..." -ForegroundColor Green

# Variables
$resourceGroup = "dynamo-detailing-rg"
$appName = "dynamo-detailing-app"
$location = "East US"
$gitRepo = "https://github.com/Drugbullets/Dynamo-Detailing.git"

# Login to Azure (if not already logged in)
Write-Host "📝 Logging into Azure..." -ForegroundColor Yellow
az login

# Create resource group
Write-Host "📁 Creating resource group..." -ForegroundColor Yellow
az group create --name $resourceGroup --location $location

# Create App Service Plan (Free tier)
Write-Host "⚡ Creating App Service Plan..." -ForegroundColor Yellow
az appservice plan create --name "${appName}-plan" --resource-group $resourceGroup --sku FREE --is-linux

# Create Web App
Write-Host "🌐 Creating Web App..." -ForegroundColor Yellow
az webapp create --resource-group $resourceGroup --plan "${appName}-plan" --name $appName --runtime "JAVA:17-java17"

# Configure deployment from GitHub
Write-Host "🔗 Setting up GitHub deployment..." -ForegroundColor Yellow
az webapp deployment source config --name $appName --resource-group $resourceGroup --repo-url $gitRepo --branch main --manual-integration

# Configure app settings
Write-Host "⚙️ Configuring app settings..." -ForegroundColor Yellow
az webapp config appsettings set --resource-group $resourceGroup --name $appName --settings `
    JAVA_OPTS="-Dspring.profiles.active=azure" `
    WEBSITES_PORT=8080 `
    MAVEN_ARGS="clean package -DskipTests"

Write-Host "✅ Deployment complete!" -ForegroundColor Green
Write-Host "🌐 Your app will be available at: https://${appName}.azurewebsites.net" -ForegroundColor Cyan
Write-Host "⏱️ Initial deployment may take 5-10 minutes..." -ForegroundColor Yellow
