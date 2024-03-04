FROM node:16 as frontend-builder

WORKDIR /app

COPY ./frontend/ChallengeFront/package*.json ./

RUN npm install -g @angular/cli@16.2.9

COPY . .

RUN npm install

RUN ng build --configuration=production

FROM nginx:1.21

COPY --from=frontend-builder /app/dist /usr/share/nginx/html

EXPOSE 8081

CMD ["nginx", "-g", "daemon off;"]
