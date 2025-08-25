# 🚀 Dynamo Detailing - Free Deployment Guide

## 📋 Prerequisites
- GitHub account
- Git installed on your computer

## 🎯 Recommended: Railway Deployment (Free $5/month credit)

### Step 1: Push to GitHub
```bash
# Initialize git repository (if not already done)
git init
git add .
git commit -m "Initial commit - Dynamo Detailing website"

# Create repository on GitHub and push
git remote add origin https://github.com/YOURUSERNAME/dynamo-detailing.git
git branch -M main
git push -u origin main
```

### Step 2: Deploy to Railway
1. Go to [railway.app](https://railway.app)
2. Sign up with GitHub
3. Click "Deploy from GitHub repo"
4. Select your dynamo-detailing repository
5. Railway will automatically:
   - Detect it's a Java Spring Boot app
   - Build using Maven
   - Deploy with the `railway.toml` configuration

### Step 3: Add Database
1. In Railway dashboard, click "New" → "Database" → "PostgreSQL"
2. Railway automatically sets these environment variables:
   - `DATABASE_URL`
   - `DATABASE_USERNAME` 
   - `DATABASE_PASSWORD`
   - `PORT`

### Step 4: Custom Domain (Optional)
- In Railway dashboard, go to your service → Settings → Domains
- Add a custom domain or use the provided railway.app subdomain

---

## 🌐 Alternative: Render (Free Tier)

### Step 1: Deploy to Render
1. Go to [render.com](https://render.com)
2. Sign up with GitHub
3. Click "New" → "Web Service"
4. Connect your GitHub repository
5. Configure:
   - **Build Command**: `mvn clean package -DskipTests`
   - **Start Command**: `java -Dspring.profiles.active=prod -Dserver.port=$PORT -jar target/detailing-1.0.0.jar`

### Step 2: Add PostgreSQL Database
1. In Render dashboard, click "New" → "PostgreSQL"
2. Copy the "Internal Database URL"
3. In your web service, go to Environment
4. Add: `DATABASE_URL` = (your database URL)

---

## ⚙️ Environment Variables

Your hosting service should set these automatically, but if needed:

```
DATABASE_URL=postgresql://username:password@host:port/database
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

---

## 🔧 Local Testing with Production Profile

```bash
# Build the application
mvn clean package -DskipTests

# Set environment variables (Windows PowerShell)
$env:SPRING_PROFILES_ACTIVE="prod"
$env:DATABASE_URL="jdbc:postgresql://localhost:5432/dynamodb"
$env:DATABASE_USERNAME="postgres"
$env:DATABASE_PASSWORD="yourpassword"

# Run with production profile
java -jar target/detailing-1.0.0.jar
```

---

## 🎉 Your Website Features

Once deployed, your website will have:

✅ **Professional Booking System**
- Complete customer information collection
- Address management (billing + service location) 
- Service selection with pricing
- Date/time scheduling
- Mobile responsive design

✅ **Admin Panel**
- Booking management
- Customer information display
- Service status updates
- Professional dashboard

✅ **Enhanced UI/UX**
- Modern, responsive design
- Form validation and error handling
- Professional styling with animations
- Mobile-first approach

---

## 📝 Next Steps After Deployment

1. **Test the booking system** - Make a test booking
2. **Admin access** - Use the admin login you configured
3. **Domain setup** - Add your custom domain if desired
4. **SSL Certificate** - Automatically provided by Railway/Render
5. **Monitor usage** - Check your hosting dashboard

---

## 🆘 Troubleshooting

### Common Issues:
1. **Build fails**: Check Java version is 17
2. **Database connection**: Verify environment variables
3. **Port issues**: Make sure `server.port=${PORT:8080}` is set

### Logs:
- **Railway**: Check build and deployment logs in dashboard
- **Render**: View logs in service dashboard

---

## 💡 Cost Breakdown

### Railway (Recommended)
- **Free**: $5 credit monthly (enough for small sites)
- **PostgreSQL**: Included in free tier
- **Custom domain**: Free
- **SSL**: Free

### Render
- **Web Service**: Free tier available (limited hours)
- **PostgreSQL**: Free 90 days, then $7/month
- **Custom domain**: Free on paid plans
- **SSL**: Free

**Best Choice**: Railway for truly free hosting with database included!
