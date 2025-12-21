#!/bin/bash

set -e

echo "🔍 Checking dependencies..."

if ! command -v docker &> /dev/null
then
  echo "❌ Docker is not installed"
  exit 1
fi

if ! command -v docker-compose &> /dev/null
then
  echo "❌ Docker Compose is not installed"
  exit 1
fi

echo "✅ Dependencies OK"

echo "🚀 Starting the application..."
docker-compose up --build

echo "🎉 App running!"
echo "Frontend: http://localhost:4200"
echo "Backend:  http://localhost:8080"