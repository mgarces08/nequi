output "db_endpoint" {
  value = aws_db_instance.mysql_db.endpoint
}

output "db_name" {
  value = aws_db_instance.mysql_db.db_name
}
