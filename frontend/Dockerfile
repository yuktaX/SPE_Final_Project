# Use Node.js v20 as the base image
FROM node:20-alpine

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application files
COPY . .

# Build the application for production
RUN npm run build

# Install a lightweight HTTP server
RUN npm install -g serve

# Expose the port Vite will serve on
EXPOSE 4173

# Serve the production build
CMD ["serve", "-s", "dist", "-l", "4173"]


# Use Node.js v20 as the base image
# FROM node:20-alpine

# # Set the working directory inside the container
# WORKDIR /app

# # Copy package.json and package-lock.json to install dependencies
# COPY package*.json ./

# # Install dependencies
# RUN npm install

# # Copy the rest of the application files
# COPY . .

# # Expose the port your app runs on
# EXPOSE 3000

# # Set the command to start your application
# CMD ["npm", "start"]
