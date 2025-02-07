from flask import Flask, jsonify
from flask_cors import CORS
import json
from collections import OrderedDict


app = Flask(__name__)
CORS(app)

token = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNT'

@app.route('/response/accGen/385386/' + token, methods=['GET'])
def get_response():
    with open('3pResponse.json', 'r') as file:
        data = json.load(file, object_pairs_hook=OrderedDict)

    return data

@app.route('/request/' + token, methods=['GET'])
def get_request():
    with open('3pRequest.json', 'r') as file:
        data = json.load(file, object_pairs_hook=OrderedDict)

    return data

@app.route('/bundler/' + token, methods=['GET'])
def get_bundler():
    with open('bundler.json', 'r') as file:
        data = json.load(file, object_pairs_hook=OrderedDict)

    return data

@app.route('/token', methods=['POST'])
def get_token():
    return jsonify(access_token=token)

if __name__ == '__main__':
    app.run(debug=True)