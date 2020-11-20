# pull official base image
FROM node:alpine as install

# set working directory
WORKDIR /app

# Copy the dependencies files
COPY package.json .
COPY package-lock.json .

# Install dependencies
RUN npm install

# Copy remaining files
COPY . .

FROM install as run_dev

EXPOSE 3000

# start app
ENTRYPOINT [ "npm", "start" ]

FROM install as build

ARG REACT_APP_BACKEND_ENDPOINT

# Build the project for production
RUN REACT_APP_BACKEND_ENDPOINT=${REACT_APP_BACKEND_ENDPOINT}\
    npm run build

# Run Stage Start
FROM nginx as run_prod

EXPOSE 80

#Copy production build files from builder phase to nginx
COPY --from=build /app/build /usr/share/nginx/html
