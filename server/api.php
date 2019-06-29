<?php
require "database_manager.php";

if ($_SERVER["REQUEST_METHOD"] === "GET") {
    handle_get_request();
} else if($_SERVER["REQUEST_METHOD"] === "POST") {
    handle_post_request();
} else if($_SERVER["REQUEST_METHOD"] === "DELETE") {
    handle_delete_request();
}

function handle_get_request() {
    $str = explode("/", $_SERVER["REQUEST_URI"]);
    if(sizeof($str) > 2) {
        if(strpos($str[2], "get_all_notes") === 0 && isset($_GET["id"])) {
            get_all_notes();
        } else if(strpos($str[2], "get_note") === 0 && isset($_GET["id"])) {
            get_note();
        } else if(sizeof($str) > 2 && $str[2] == "logout") {
            handle_logout();
        }
    }
}

function handle_post_request() {
    $str = explode("/", $_SERVER["REQUEST_URI"]);
    if(sizeof($str) > 2 && $str[2] == "register") {
        register();
    } else if(sizeof($str) > 2 && $str[2] == "login") {
        login();
    } else if(sizeof($str) > 2 && $str[2] == "create_note") {
        create_note();
    } else if(sizeof($str) > 2 && $str[2] == "update_note") {
        update_note();
    }
}

function handle_delete_request() {
    $str = explode("/", $_SERVER["REQUEST_URI"]);
    if(sizeof($str) > 2) {
        if(strpos($str[2], "delete_note") === 0 && isset($_GET["id"])) {
            delete_note();
        }
    }
}

function register() {
    $data = file_get_contents("php://input");
    $user = json_decode($data, true);
    $database_manager = new database_manager();
    $hash = password_hash($user["password"], PASSWORD_DEFAULT);
    if($database_manager->add_user($user["username"], $hash, $user["email"])) {
        echo json_encode(["register" => $user], JSON_UNESCAPED_UNICODE);
    }
}

function login() {
    $data = file_get_contents("php://input");
    $user = json_decode($data, true);
    $database_manager = new database_manager();
    $hash = password_hash($user["password"], PASSWORD_DEFAULT);
    $result = $database_manager->get_user_by_username($user["username"]);
    if($result && password_verify($user["password"], $result[0]["password"])) {
        echo json_encode($result[0], JSON_UNESCAPED_UNICODE);
    }
}

function create_note() {
    $data = file_get_contents("php://input");
    $content = json_decode($data, true);
    $database_manager = new database_manager();
    $result = $database_manager->insert_note($content["note"]["title"], $content["note"]["content"], $content["id"]);
    echo json_encode($result, JSON_UNESCAPED_UNICODE);
}

function update_note() {
    $data = file_get_contents("php://input");
    $content = json_decode($data, true);
    $database_manager = new database_manager();
    $result = $database_manager->update_note($content["title"], $content["content"], $content["id"]);
}

function get_all_notes() {
    $database_manager = new database_manager();
    echo json_encode($database_manager->get_all_notes(($_GET["id"])), JSON_UNESCAPED_UNICODE);
}

function get_note() {
    $database_manager = new database_manager();
    echo json_encode($database_manager->get_note($_GET["id"])[0], JSON_UNESCAPED_UNICODE);
}

function delete_note() {
    $database_manager = new database_manager();
    $database_manager->delete_note($_GET["id"]);
}
?>