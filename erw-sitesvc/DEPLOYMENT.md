# 🚀 ERW Platform - Free Hosting Deployment Guide

## Option 1: Railway (Recommended - Easiest with Free PostgreSQL)

### Step 1: Prepare Repository
✅ **Already completed!** Your repo has:
- `railway.json` - Railway configuration
- `Procfile` - Web service startup
- Dynamic port configuration in `application.yml`
- Environment variable setup

### Step 2: Deploy to Railway

1. **Go to [railway.app](https://railway.app)** and sign up with GitHub
2. **Click "Deploy from GitHub repo"**
3. **Select your `erw-city-viability` repository**
4. **Railway will auto-detect Spring Boot and deploy!**

### Step 3: Add PostgreSQL Database

1. In your Railway dashboard, click **"+ New"** → **"Database"** → **"PostgreSQL"**
2. Railway will automatically set these environment variables:
   - `DATABASE_URL`
   - `PGUSER` 
   - `PGPASSWORD`
   - `PGHOST`
   - `PGPORT`
   - `PGDATABASE`

### Step 4: Configure Environment Variables

Railway auto-creates the database URL, but you may need to add:
```bash
DB_URL=${DATABASE_URL}
DB_USER=${PGUSER}  
DB_PASS=${PGPASSWORD}
```

### Step 5: Get Your Live URL
Railway will provide a URL like: `https://your-app-name.up.railway.app`

---

## Option 2: Render (Alternative)

1. **Go to [render.com](https://render.com)** and connect GitHub
2. **Create Web Service** from your repo
3. **Settings:**
   - Build Command: `./mvnw clean package -DskipTests`
   - Start Command: `java -Dserver.port=$PORT -jar target/*.jar`
4. **Add PostgreSQL** from Render dashboard
5. **Environment Variables:**
   - `DB_URL` - From PostgreSQL connection string
   - `DB_USER` - From PostgreSQL credentials  
   - `DB_PASS` - From PostgreSQL credentials

---

## Option 3: Heroku (Classic)

```bash
# Install Heroku CLI first
heroku create your-erw-app
heroku addons:create heroku-postgresql:essential-0  # Free tier
git push heroku main
```

---

## 🔧 Troubleshooting

### Common Issues:
- **Build fails:** Ensure Java 21 is specified in `system.properties`
- **Database connection:** Check environment variables match service names
- **Port issues:** Make sure `${PORT:8080}` is in application.yml
- **Memory issues:** Spring Boot needs ~512MB minimum

### Verification Steps:
1. Check build logs for errors
2. Test health endpoint: `your-url.com/api/health`
3. Test API: `your-url.com/api/sites`
4. Verify web interface loads

---

## 📊 Expected Performance on Free Tier

- **Railway:** 512MB RAM, unlimited hours, PostgreSQL included
- **Render:** 512MB RAM, sleeps after 15min inactivity
- **Heroku:** 550 dyno hours/month, sleeps after 30min

**Your ERW platform should run perfectly on any of these!** 🎉

---

## 🚀 Quick Railway Deploy (Recommended)

**Just follow these 3 clicks:**

1. **[railway.app](https://railway.app)** → Sign up with GitHub
2. **"Deploy from GitHub repo"** → Select `erw-city-viability`  
3. **Add PostgreSQL database** → Railway handles the rest!

**Done! Your ERW City Viability Analysis Platform will be live in ~3 minutes.**