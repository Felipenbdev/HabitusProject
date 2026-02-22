# 📌 Habitus

Habitus é um aplicativo de gerenciamento de tarefas pessoais, composto por:

- 🔹 **HabitusProject** → Aplicação Android (Frontend)
- 🔹 **ApiHabitusProject** → API Backend responsável por autenticação e gerenciamento de tarefas

O objetivo do projeto é permitir que usuários criem uma conta, façam login e gerenciem suas tarefas em formato de checklist.

---

# 🧱 Arquitetura

Android (Frontend)  
⬇  
API REST (Spring Boot - Docker)  
⬇  
PostgreSQL (Docker)  
⬇  
pgAdmin (Docker)

---

# 🚀 Funcionalidades

## 👤 Autenticação
- Criar usuário
- Realizar login

## ✅ Tarefas
- Criar tarefa
- Listar tarefas do usuário
- Marcar tarefa como concluída
- Definir data e horário da tarefa
- Notificar Usuário sobre a tarefa

---

# 🐳 Executando o Backend com Docker

## 1️⃣ Pré-requisitos

- Docker Desktop instalado
- Docker Compose habilitado

---

## 2️⃣ Clonar o Backend

```bash
git clone https://github.com/Felipenbdev/ApiHabitusProject.git
```

---

## 3️⃣ Subir os Containers

```bash
docker compose up --build
```

## Isso irá iniciar:

- 🐘 PostgreSQL → Porta 5432

- 🌐 pgAdmin → http://localhost:5050

- 🚀 Backend → http://localhost:8080

## 🖥️ Como Usar o pgAdmin

Acesse → http://localhost:5050

### Login do pgAdmin:

- Email: admin@habitus.com

- Senha: admin

### 🔗 Conectar ao Banco pelo pgAdmin

### 1. Clique em Add New Server

### 2. Aba General
- Name: Habitus

### 3. Aba Connection

- Host name/address: db

- Port: 5432

- Username: postgres

- Password: postgres

### 4. Clique em Save

Agora você poderá visualizar:

- Banco habitus

- Tabelas criadas automaticamente pelo Hibernate

# 📱 Como executar o App Android

## 1️⃣ Clonar o repositório

Clone o projeto para sua máquina:

```bash
git clone https://github.com/Felipenbdev/HabitusProject.git
```
## 2️⃣ Abrir no Android Studio

- Abra o Android Studio

- Clique em Open

- Selecione a pasta do projeto clonado

- Aguarde o Gradle sincronizar completamente

## 3️⃣ Configurar URL da API

- Verifique se o aplicativo está configurado para acessar a API no seguinte endereço:

```bash
http://10.0.2.2:8080
```

⚠️ Importante:

- Para emulador Android, utilize 10.0.2.2 no lugar de localhost.

- Para dispositivo físico, utilize o IP local da sua máquina (ex: http://192.168.0.10:8080).

## 4️⃣ Executar o aplicativo

### 1. Selecione um dispositivo:

- Emulador Android

- Dispositivo físico conectado via USB

### 2. Clique no botão Run ▶

- O aplicativo será compilado e iniciado automaticamente no dispositivo selecionado.
