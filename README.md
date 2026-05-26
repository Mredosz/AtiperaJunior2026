# GitHub Repositories API

Recruitment project developed as part of the recruitment process for Atipera.

## 📌 Task Description

This API allows fetching non-fork GitHub repositories of a given user, including a list of branches in each repository and the SHA of the last commit on each branch.

### Endpoint

`GET http://localhost:8080/users/{username}/repositories`

### Example Response

```json
[
  {
    "repositoryName": "AtiperaJunior2026",
    "ownerLogin": "Mredosz",
    "branches": [
      {
        "name": "main",
        "sha": "d5b9c317be7370fa3687d32dc7cb5ba62e436269"
      }
    ]
  }
]
```

### Error Response (404)

```json
{
  "status": 404,
  "message": "GitHub user not found"
}
```