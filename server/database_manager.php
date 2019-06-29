<?php

class database_manager {
    private $ADD_USER = "INSERT INTO users (username, password, email) 
                         VALUES (?, ?, ?);";

    private $GET_USER_BY_USERNAME = "SELECT id, username, password, email 
                                  FROM users 
                                  WHERE username = ?;";

    private $GET_ALL_NOTES = "SELECT notes.id, notes.title, notes.content
                              FROM notes INNER JOIN users ON (notes.created_by = users.id)
                              WHERE notes.created_by = ?;";

    private $GET_NOTE = "SELECT id, title, content
                         FROM notes
                         WHERE id = ?;";

    private $INSERT_NOTE = "INSERT 
                            INTO notes (title, content, created_by)
                            VALUES (?, ?, ?);";

    private $UPDATE_NOTE = "UPDATE notes
                            SET title = ?, content = ?
                            WHERE id = ?;";

    private $DELETE_NOTE = "DELETE 
                            FROM notes
                            WHERE id = ?;";

    public function add_user($username, $password, $email) {
        return $this->execute_query($this->ADD_USER, array($username, $password, $email));
    }

    public function get_user_by_username($username) {
        return $this->select_query($this->GET_USER_BY_USERNAME, array($username));
    }

    public function get_all_notes($id) {
        return $this->select_query($this->GET_ALL_NOTES, array($id));
    }

    public function get_note($id) {
        return $this->select_query($this->GET_NOTE, array($id));
    }

    public function insert_note($title, $content, $created_by) {
        return $this->execute_query($this->INSERT_NOTE, array($title, $content, $created_by));
    }

    public function update_note($title, $content, $id) {
        return $this->execute_query($this->UPDATE_NOTE, array($title, $content, $id));
    }

    public function delete_note($id) {
        return $this->execute_query($this->DELETE_NOTE, array($id));
    }

    private function execute_query($query, $values) {
        $credentials = "mysql:host=localhost;dbname=mobileapps;charset=utf8";
        $user = "root";
        $password = "";
        $conn = new PDO($credentials, $user, $password);
        $stmt = $conn->prepare($query);
        return $stmt->execute($values);
    }

    private function select_query($query, $values) {
        $credentials = "mysql:host=localhost;dbname=mobileapps;charset=utf8";
        $user = "root";
        $password = "";
        $conn = new PDO($credentials, $user, $password);
        $stmt = $conn->prepare($query);
        if($stmt->execute($values)) {
            return $stmt->fetchAll(PDO::FETCH_ASSOC);
        } else return false;
    }
}

?>