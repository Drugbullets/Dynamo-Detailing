# 🚀 Deploy Dynamo Detailing to Render.com - Step by Step

## 🎯 Follow these exact steps to get your website live for FREE!

---

## Step 1: Go to Render.com
1. Open your browser
2. Go to: **https://render.com**
3. Click the **"Get Started for Free"** button

## Step 2: Sign Up with GitHub
1. Click **"GitHub"** to sign up with your GitHub account
2. **Authorize Render** to access your repositories
3. You'll be taken to the Render dashboard

## Step 3: Create New Web Service
1. On the Render dashboard, click **"New +"** button (top right)
2. Select **"Web Service"** from the dropdown
3. You'll see "Build and deploy from a Git repository"

## Step 4: Connect Your GitHub Repository
1. Click **"Connect GitHub"** if not already connected
2. In the repository list, find and select:
   **"Drugbullets/Dynamo-Detailing"**
3. Click **"Connect"** next to your repository

## Step 5: Configure Your Service
Fill in these EXACT settings:

### Basic Settings:
- **Name**: `dynamo-detailing` (or any name you prefer)
- **Region**: Leave default (Oregon, USA)
- **Branch**: `main`
- **Root Directory**: Leave empty
- **Runtime**: `Java`

### Build & Deploy Settings:
- **Build Command**: 
  ```
  mvn clean package -DskipTests
  ```
- **Start Command**: 
  ```
  java -Dspring.profiles.active=free -Dserver.port=$PORT -jar target/detailing-1.0.0.jar
  ```

### Instance Type:
- **Select**: **Free** (0.1 CPU, 512 MB RAM)

## Step 6: Deploy Your Application
1. Click **"Create Web Service"** button
2. Render will start building your application
3. You'll see the build logs in real-time
4. **Wait 5-10 minutes** for the build to complete

## Step 7: Get Your Live URL
1. Once deployed, you'll see: **"Your service is live"**
2. Your website URL will be: **`https://dynamo-detailing-XXXX.onrender.com`**
3. Click the URL to visit your live website!

---

## 🎉 Your Live Website Pages:

After deployment, you'll have:
- **Homepage**: `https://your-url.onrender.com/`
- **Book Service**: `https://your-url.onrender.com/book`
- **Admin Login**: `https://your-url.onrender.com/admin/login`
- **Services**: `https://your-url.onrender.com/services`
- **About**: `https://your-url.onrender.com/about`
- **Contact**: `https://your-url.onrender.com/contact`

### Admin Credentials:
- **Username**: `admin`
- **Password**: `admin123`

---

## 📋 Expected Build Process:

You'll see these steps in the build log:
1. **Cloning repository** from GitHub
2. **Detecting Java** environment
3. **Running Maven build** (`mvn clean package`)
4. **Creating JAR file** (`detailing-1.0.0.jar`)
5. **Starting application** with H2 database
6. **Service live** at your Render URL

---

## 🔧 If You Encounter Issues:

### Build Fails:
- Check that your GitHub repository is **public**
- Verify the build command: `mvn clean package -DskipTests`
- Check the build logs for specific error messages

### Service Won't Start:
- Verify start command: `java -Dspring.profiles.active=free -Dserver.port=$PORT -jar target/detailing-1.0.0.jar`
- Check that the JAR file was created in the `target/` directory

### Can't Access Website:
- Make sure the service shows "Live" status
- Wait a few minutes after deployment
- Check that the URL is correct

---

## 💰 Free Tier Limitations:

### What's FREE Forever:
- ✅ **750 hours/month** of runtime
- ✅ **SSL certificate** (HTTPS)
- ✅ **Custom domains** supported
- ✅ **GitHub integration**
- ✅ **Build logs and monitoring**

### Limitations:
- ⏰ **Service sleeps** after 15 min of inactivity (restarts when accessed)
- 📊 **512 MB RAM** limit
- 🔄 **Slow cold starts** (first request after sleep)

---

## 🎊 Success! Your Professional Website Features:

### Customer Features:
- ✅ Professional homepage with service showcase
- ✅ Complete booking system (with fixed address fields)
- ✅ Mobile responsive design
- ✅ Professional styling and animations

### Admin Features:
- ✅ Admin dashboard at `/admin/login`
- ✅ Booking management system
- ✅ Customer information display
- ✅ Service status updates

### Technical Features:
- ✅ H2 file database (completely free)
- ✅ SSL certificate (HTTPS)
- ✅ Professional URL
- ✅ Automatic deployments from GitHub

---

## 🔄 Future Updates:

To update your live website:
1. Make changes to your code locally
2. Commit and push to GitHub
3. Render will automatically rebuild and deploy!

---

**🚀 Ready to deploy? Follow the steps above and your professional auto detailing website will be live in 10 minutes!** 🎉
